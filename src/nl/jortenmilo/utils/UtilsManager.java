package nl.jortenmilo.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Mixer;

import nl.jortenmilo.utils.math.CalculatorUtils;
import nl.jortenmilo.utils.math.MathUtils;
import nl.jortenmilo.utils.math.RandomUtils;
import nl.jortenmilo.utils.net.NetBot;
import nl.jortenmilo.utils.net.SocketClient;
import nl.jortenmilo.utils.net.SocketServer;
import nl.jortenmilo.utils.net.WebClient;
import nl.jortenmilo.utils.net.WebServer;
import nl.jortenmilo.utils.sound.MidiUtils;
import nl.jortenmilo.utils.sound.MixerUtils;
import nl.jortenmilo.utils.sound.RecorderUtils;
import nl.jortenmilo.utils.sound.SoundUtils;

public class UtilsManager {
	
	private List<UtilsEventListener> listeners = new ArrayList<UtilsEventListener>();
	
	public IDUtils createIDUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("IDUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new IDUtils();
	}
	
	public StringUtils createStringUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("StringUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new StringUtils();
	}
	
	public SystemUtils createSystemUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("SystemUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new SystemUtils();
	}
	
	public MathUtils createMathUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("MathUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new MathUtils();
	}
	
	public CalculatorUtils createCalculatorUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("CalculatorUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new CalculatorUtils();
	}
	
	public RandomUtils createRandomUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("RandomUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new RandomUtils();
	}
	
	public NetBot createNetBot() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("NetBot");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new NetBot();
	}
	
	public SocketClient createSocketClient() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("SocketClient");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new SocketClient();
	}
	
	public SocketServer createSocketServer() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("SocketServer");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new SocketServer();
	}
	
	public WebClient createWebClient() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("WebClient");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new WebClient();
	}
	
	public WebServer createWebServer() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("WebServer");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new WebServer();
	}
	
	public MidiUtils createMidiUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("MidiUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new MidiUtils();
	}
	
	public MixerUtils createMixerUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("MixerUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new MixerUtils();
	}
	
	public RecorderUtils createRecorderUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("RecorderUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new RecorderUtils();
	}
	
	public SoundUtils createSoundUtils(File f, Mixer m) {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("SoundUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new SoundUtils(f, m);
	}
	
	public IDUtils cloneIDUtils(IDUtils u) {
		IDUtils nu = new IDUtils();
		nu.setUUDIs(u.getUUIDs());
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("IDUtils");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public StringUtils cloneStringUtils(StringUtils u) {
		StringUtils nu = new StringUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("StringUtils");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public SystemUtils cloneSystemUtils(SystemUtils u) {
		SystemUtils nu = new SystemUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("SystemUtils");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public MathUtils cloneMathUtils(MathUtils u) {
		MathUtils nu = new MathUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("MathUtils");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public CalculatorUtils cloneCalculatorUtils(CalculatorUtils u) {
		CalculatorUtils nu = new CalculatorUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("CalculatorUtils");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public RandomUtils cloneRandomUtils(RandomUtils u) {
		RandomUtils nu = new RandomUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("RandomUtils");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public NetBot cloneNetBot(NetBot u) {
		NetBot nu = new NetBot();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("RandomUtils");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public SocketClient cloneSocketClient(SocketClient u) {
		SocketClient nu = new SocketClient();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("SocketClient");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public SocketServer cloneSocketServer(SocketServer u) {
		SocketServer nu = new SocketServer();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("SocketServer");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public WebClient cloneWebClient(WebClient u) {
		WebClient nu = new WebClient();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("WebClient");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public WebServer cloneWebServer(WebServer u) {
		WebServer nu = new WebServer();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("WebServer");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public MidiUtils cloneMidiUtils(MidiUtils u) {
		MidiUtils nu = new MidiUtils();
		nu.setInstruments(u.getInstruments());
		nu.setSynthesizer(u.getSynthesizer());
		nu.setChannels(u.getChannels());
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("WebServer");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public MixerUtils cloneMixerUtils(MixerUtils u) {
		MixerUtils nu = new MixerUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("MixerUtils");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public RecorderUtils cloneRecorderUtils(RecorderUtils u) {
		RecorderUtils nu = new RecorderUtils();
		nu.setBigEndian(u.isBigEndian());
		nu.setSigned(u.isSigned());
		nu.setChannels(u.getChannels());
		nu.setFileType(u.getFileType());
		nu.setRecordTime(u.getRecordTime());
		nu.setSampleRate(u.getSampleRate());
		nu.setSampleSizeInBits(u.getSampleSizeInBits());
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("RecorderUtils");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public SoundUtils cloneSoundUtils(SoundUtils u) {
		SoundUtils nu = new SoundUtils(u.getFile(), u.getMixer());
		nu.setBalance(u.getBalance());
		nu.setMute(u.isMuted());
		nu.setPan(u.getPan());
		nu.setPosition(u.getSoundPosition());
		nu.setVolume(u.getVolume());
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("SoundUtils");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
}