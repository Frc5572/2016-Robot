package net.util5572.comm;

import java.nio.ByteBuffer;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.RawData;

public class NVidia {

	private static com.ni.vision.NIVision.Image image;

	public static void init() {
		image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB_U64, 1);
	}

	private static int getInt(byte[] bytes, int point) {
		return ByteBuffer.wrap(sect(bytes, point, 4)).getInt();
	}

	private static byte[] sect(byte[] a, int start, int amnt) {
		byte[] b = new byte[amnt];
		for (int i = 0; i < amnt; i++) {
			b[i] = a[start + i];
		}
		return b;
	}

	private static byte[] sect(byte[] a, int start) {
		return sect(a, start, a.length);
	}

	private static void update(byte[] k) {
		int width = getInt(k, 0);
		NIVision.imaqArrayToImage(image,
				new RawData(ByteBuffer.wrap(sect(k, 4))), width, k.length
						/ (4 * width));
	}

}
