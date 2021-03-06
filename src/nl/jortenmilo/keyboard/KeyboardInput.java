package nl.jortenmilo.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleUser;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This is the KeyboardInput class. It instantiates the KeyListener and is thereby the core class for key logging.
 * @see KeyboardManager
 */
public class KeyboardInput implements KeyListener {
	
	private EventManager events;
	
	private HashMap<Integer, Boolean> pressed = new HashMap<Integer, Boolean>();
	private List<Integer> wait = new ArrayList<Integer>();
	private CountDownLatch latch;
	private int typed = -1;
	
	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		
		if(c=='\n') {
			Console.debug("KEY_PRESSED [" + new SystemUtils().getTime() + "][][" + e.getKeyCode() + "][" + KeyEvent.getKeyModifiersText(e.getModifiers()) + "][" + KeyEvent.getKeyText(e.getKeyCode()) + "]");
		} else {
			Console.debug("KEY_PRESSED [" + new SystemUtils().getTime() + "][" + e.getKeyChar() + "][" + e.getKeyCode() + "][" + KeyEvent.getKeyModifiersText(e.getModifiers()) + "][" + KeyEvent.getKeyText(e.getKeyCode()) + "]");
		}
		
		KeyboardPressedEvent event = new KeyboardPressedEvent();
		event.setKeyChar(e.getKeyChar());
		event.setKeyCode(e.getKeyCode());
		event.setKeyText(KeyEvent.getKeyText(e.getKeyCode()));
		event.setModifiersText(KeyEvent.getKeyModifiersText(e.getModifiers()));
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		pressed.put(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char c = e.getKeyChar();
		
		if(c=='\n') {
			Console.debug("KEY_RELEASED [" + new SystemUtils().getTime() + "][][" + e.getKeyCode() + "][" + KeyEvent.getKeyModifiersText(e.getModifiers()) + "][" + KeyEvent.getKeyText(e.getKeyCode()) + "]");
		} else {
			Console.debug("KEY_RELEASED [" + new SystemUtils().getTime() + "][" + e.getKeyChar() + "][" + e.getKeyCode() + "][" + KeyEvent.getKeyModifiersText(e.getModifiers()) + "][" + KeyEvent.getKeyText(e.getKeyCode()) + "]");
		}
			
		KeyboardReleasedEvent event = new KeyboardReleasedEvent();
		event.setKeyChar(e.getKeyChar());
		event.setKeyCode(e.getKeyCode());
		event.setKeyText(KeyEvent.getKeyText(e.getKeyCode()));
		event.setModifiersText(KeyEvent.getKeyModifiersText(e.getModifiers()));
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		pressed.put(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		
		if(c=='\n') {
			Console.debug("KEY_TYPED [" + new SystemUtils().getTime() + "][][" + e.getKeyCode() + "][" + KeyEvent.getKeyModifiersText(e.getModifiers()) + "][" + KeyEvent.getKeyText(e.getKeyCode()) + "]");
		} else {
			Console.debug("KEY_TYPED [" + new SystemUtils().getTime() + "][" + e.getKeyChar() + "][" + e.getKeyCode() + "][" + KeyEvent.getKeyModifiersText(e.getModifiers()) + "][" + KeyEvent.getKeyText(e.getKeyCode()) + "]");
		}
			
		KeyboardTypedEvent event = new KeyboardTypedEvent();
		event.setKeyChar(e.getKeyChar());
		event.setKeyCode(e.getKeyCode());
		event.setKeyText(KeyEvent.getKeyText(e.getKeyCode()));
		event.setModifiersText(KeyEvent.getKeyModifiersText(e.getModifiers()));
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		if(wait.size() == 1) {
			if(wait.get(0) == 0) {
				typed = e.getKeyChar();
				countDown();
				wait.clear();
				return;
			}
		}
		
		for(int i = 0; i < wait.size(); i++) {
			int n = wait.get(i);
			
			if(n == e.getKeyCode()) {
				typed = e.getKeyChar();
				countDown();
				wait.clear();
			}
		}
	}
	
	private void countDown() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				latch.countDown();
			}
		});
		
		t.start();
	}
	
	protected int waitUntilTyped() {
		if(latch != null) {
			Console.println(ConsoleUser.Error, "There is already a latch waiting for a key to be pressed!");
			return -1;
		}
		
		latch = new CountDownLatch(1);
		
		wait.add(0);
		
		try {
			latch.await();
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
		
		int typed2 = typed;
		typed = -1;
		return typed2;
	}
	
	protected void waitUntilTyped(int key) {
		if(latch != null) {
			Console.println(ConsoleUser.Error, "There is already a latch waiting for a key to be pressed!");
			return;
		}
		
		latch = new CountDownLatch(1);
		
		wait.add(key);
		
		try {
			latch.await();
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}
	
	protected boolean isPressed(int key) {
		if(pressed.containsKey(key)) {
			return pressed.get(key);
		}
		
		return false;
	}
	
	protected int waitUntilTyped(int[] keys) {
		for(int i = 0; i < keys.length; i++) {
			wait.add(keys[i]);
		}
		
		latch = new CountDownLatch(1);
		
		try {
			latch.await();
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
		
		int typed2 = typed;
		typed = -1;
		return typed2;
	}
	
	protected void setEventManager(EventManager events) {
		this.events = events;
	}
}
