package invaders;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public final class Sound {

	//private URL[] _urls = new URL[10];
	private static AudioClip[] _clips = new AudioClip[10];
	public static void LoadResources()
	{
		for (int i=0; i < 10; i++)
		{
			URL url = Sound.class.getResource("/resources/"+i+".wav");
			_clips[i] = Applet.newAudioClip(url);
		}
	}
	public static void PlaySound(int resource)
	{				
		_clips[resource].play();			
	}
}
