import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ConversorDat {
	// ======================= CONVERS�ES ========================
		
	//-------- CHAR ---------
	public static byte[] charToByteArray(char c){
		short temp = (short)c;
		return shortToByteArray(temp);
	}
	public static char byteArrayToChar(byte[] b){
		short temp = byteArrayToShort(b);
		return (char)temp;
	}
		
	//-------- STRING --------
	public static byte[] stringToByteArray(String s){
		byte[] temp = new byte[s.length()*2];
		temp = s.getBytes();
		return temp;
	}
	public static String byteArrayToString(byte[] b){
		String temp = "";
		for(int i = 0; i<b.length; i++){
			temp = temp+(char)b[i];
		}
		return temp;
	}
		
	//--------- BOOLEAN -------
	public static byte booleanToByte(boolean bool){
		if(bool){
			return 1;
		}
		else{
			return 0;
		}
	}
	public static boolean byteToBoolean(byte b){
		if(b == 0){
			return false;
		}
		else{
			return true;
		}
	}
		
	//-------- SHORT ---------
	public static byte[] shortToByteArray(short p){
		byte[] b = new byte[2];
			
		for(int i=0; i<b.length; i++){
			int offset = (b.length -1 -i)*8;
			b[i] = (byte)((p >>> offset) & 0xFF);
		}
		return b;
	}
	public static short byteArrayToShort(byte[] bArray){
		short value = 0;
			
		for(int i=0; i<2; i++){
			int shift = (2 -1 -i);
			value += (bArray[i] & 0x000000FF) << shift;
		}
			
		return value;
	}
		
	//--------- INT ---------
	public static int byteArrayToInt(byte[] bArray){
		int value = 0;
			
		for(int i=0; i<4; i++){
			int shift = (4 -1 -i)*8;
			value += (bArray[i] & 0x000000FF) << shift;
			//System.out.println(i + " " + (bArray[i] & 0x000000FF));
			//System.out.println(i + " " + value);

		}
			
		return value;
	}
	public static byte[] intToByteArray(int p){
		byte[] b = new byte[4];
	        
		for(int i =0; i<b.length; i++){
	       	int offset = (b.length -1 -i)*8;
	       	b[i] = (byte)((p >>> offset) & 0xFF);
		}
		return b;
	}
		
	//------------ LONG -------------
	public static byte[] longToByteArray(long p){
			
		byte[] b = new byte[8];
			
		for(int i=0; i<b.length; i++){
			int offset = (b.length -1 -i)*8;
			b[i] = (byte)((p >>> offset) & 0xFF);
		}
		return b;
	}
	public static long byteArrayToLong(byte[] bArray){
		long value = 0;
			
		for(int i=0; i<8; i++){
			int shift = (8 -1 -i);
			value += (bArray[i] & 0x000000FF) << shift;
		}
			
		return value;
	}
		
//=====================================================================================
		
		
		
	public static void arquivoToBytes(Catalogo catalogo){
		try{
			//abrir pra leitura arquivo 1
			FileReader file = new FileReader(catalogo.getLocalArquivo());
			BufferedReader reader = new BufferedReader(file);

			//abrir pra escrita arquivo 2
			//FileWriter file2 = new FileWriter(catalogo.getLocalDat());
			//BufferedWriter writer = new BufferedWriter(file2);
				
			RandomAccessFile writer = new RandomAccessFile(catalogo.getLocalDat(), "rw");
				
			String str = reader.readLine();
			while(str != null){
				String array[] = str.split("\t");
				for(int i = 0; i < catalogo.getnCol(); i++){
					//System.out.println(array[i]);
					byte bytes[];
					switch(catalogo.getTipoCol(i)){
						case 1:
							//INT
							bytes = intToByteArray(Integer.parseInt(array[i]));
							writer.write(bytes);
							//write(bytes[0]+"|"+bytes[1]+"|"+bytes[2]+"|"+bytes[3]);
							//System.out.println(bytes[0]+"|"+bytes[1]+"|"+bytes[2]+"|"+bytes[3]);
							break;
						case 2:
							//STRING
							bytes = stringToByteArray(array[i]);
							writer.writeInt(bytes.length);
							writer.write(bytes);
							//System.out.println(stringToByteArray(array[i]).toString());
							break;
						default:
							System.out.println("ERRO NA CONVERS�O PARA ARQUIVO DAT! TIPO NAO RECONHECIDO!");
							break;
					}
				}
				str = reader.readLine();
			}
			file.close();
			reader.close();
			//file2.close();
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

