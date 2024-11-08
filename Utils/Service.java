//package PBL4.Utils;
//
//public class Service {
//	public static byte[] combineArrays(byte[] array1, byte[] array2) {
//		byte[] combined = new byte[array1.length + array2.length];
//
//		System.arraycopy(array1, 0, combined, 0, array1.length);
//
//		System.arraycopy(array2, 0, combined, array1.length, array2.length);
//
//		return combined;
//	}
//
//	public static Integer[] splitMsg(byte[] data, int n) {
//		Integer[] result = new Integer[n];
//		int cnt = 0;
//		for (int i = 1; i < data.length; i++) {
//			if (data[i] == ' ' || data[i] == 0) {
//				result[cnt] = i;
//				if (++cnt >= n)
//					break;
//			}
//		}
//		return result;
//	}
//
//	public static byte createHeader(int roomId, int packetNum, int firstLength) {
//		return (byte) ((roomId << (8 - firstLength)) | packetNum);
//	}
//	
//	public static int[] getHeader(byte header, int firstLength) {
//		int secondLength = 8 - firstLength;
//		int[] decode = new int[2];
//		decode[0] = header >> secondLength & ((1 << firstLength) - 1);
//		decode[1] = (1 << secondLength) - 1 & header;
//		return decode;
//	}
//}


