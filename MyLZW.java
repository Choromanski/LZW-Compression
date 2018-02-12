public class MyLZW{

    private static final int R = 256;
    private static int L = 512;
    private static int W = 9;
    private static final int L_MIN = 512;
    private static final int W_MIN = 9;
    private static final int L_MAX = 65536;
    private static final int W_MAX = 16;

	private static double compressedSize = 0;
	private static double uncompressedSize = 0;
	private static double oldRatio;


    public static void compress(char mode) {
        if(mode == 'n'){
            BinaryStdOut.write(0, 2);
        }else if(mode == 'r'){
            BinaryStdOut.write(1, 2);
        }else{
            BinaryStdOut.write(2, 2);
        }

        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);
            BinaryStdOut.write(st.get(s), W);
            int t = s.length();
            if(mode == 'm' && t < input.length() && code >= L_MAX){
                uncompressedSize += t*8;
                compressedSize += W;
                if(oldRatio/(uncompressedSize/compressedSize) >= 1.1){
                    st = new TST<Integer>();
                    for(int i = 0; i < R; i++)
                        st.put("" + (char) i, i);
                    code = R+1;
                    W = W_MIN;
                    L = L_MIN;
                }
            }else if(mode == 'm' && code < L_MAX){
                uncompressedSize += t*8;
                compressedSize += W;
                oldRatio = uncompressedSize/compressedSize;

            }
            if (t < input.length() && code < L_MAX){
                st.put(input.substring(0, t + 1), code++);
                if(code > L && W < W_MAX){
                    W++;
                    L *= 2;
                }
            }else if(mode == 'r' && code >= L_MAX){
                st = new TST<Integer>();
                for (int i = 0; i < R; i++)
                    st.put("" + (char) i, i);
                code = R+1;
                W = W_MIN;
                L = L_MIN;
            }
            input = input.substring(t);
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }


    public static void expand() 
	{
		int mode = BinaryStdIn.readInt(2);
		if(mode == 0){
			String[] st = new String[L_MAX];
			int i;

	        for (i = 0; i < R; i++)
	            st[i] = "" + (char) i;
	        st[i++] = "";

	        int codeword = BinaryStdIn.readInt(W);
	        if (codeword == R) return;
	        String val = st[codeword];

	        while (true){
	            BinaryStdOut.write(val);
				codeword = BinaryStdIn.readInt(W);
	            if (codeword == R) break;
				String s = st[codeword];

				if (i == codeword) s = val + val.charAt(0);
	            if (i < L_MAX){
					st[i++] = val + s.charAt(0);
				}
				if (i >= L && W < W_MAX){
					W++;
					L *= 2;
				}
				val = s;
				
	        }
		    BinaryStdOut.close();
		}else if (mode == 1){
			String[] st = new String[L_MAX];
			int i;

	        for (i = 0; i < R; i++)
	            st[i] = "" + (char) i;
	        st[i++] = "";

	        int codeword = BinaryStdIn.readInt(W);
	        if (codeword == R) return;
	        String val = st[codeword];

	        while (true){
	            BinaryStdOut.write(val);
				if(i < L_MAX)
					codeword = BinaryStdIn.readInt(W);
				else
					codeword = BinaryStdIn.readInt(9);

				if (codeword == R) break;
				String s = st[codeword];

				if (i == codeword) s = val + val.charAt(0);

	            if (i < L_MAX){
					st[i++] = val + s.charAt(0);
				}else{
					st = new String[L_MAX];
					for(i = 0; i < R; i++)
						st[i] = "" + (char) i;
					st[i++] = "";
					W = W_MIN;
					L = L_MIN;
				}


				if (i >= L && W < W_MAX){
					W++;
					L *= 2;
				}
				val = s;
	        }
		    BinaryStdOut.close();
		}else{
			String[] st = new String[L_MAX];
			int i;

	        for (i = 0; i < R; i++)
	            st[i] = "" + (char) i;
	        st[i++] = "";

	        int codeword = BinaryStdIn.readInt(W);
	        if (codeword == R) return;
	        String val = st[codeword];

	        while (true){
	            BinaryStdOut.write(val);
				
				if(i < L_MAX){
					uncompressedSize += val.length()*8;
					compressedSize += W;
					oldRatio = uncompressedSize/compressedSize;
				}else{
					uncompressedSize += val.length()*8;
					compressedSize += W;
				}
				if(i >= L_MAX && (oldRatio)/(uncompressedSize/compressedSize) >= 1.1){
					st = new String[L_MAX];
					for(i = 0; i < R; i++)
						st[i] = "" + (char) i;
					st[i++] = "";
					W = W_MIN;
					L = L_MIN;
				}
				

				if (i >= L && W < W_MAX){
					W++;
					L *= 2;
				}

				codeword = BinaryStdIn.readInt(W);
	            if (codeword == R) break;
				String s = st[codeword];

				if (i == codeword) s = val + val.charAt(0);

	            if (i < L_MAX){
					st[i++] = val + s.charAt(0);
				}
				val = s;
				
	        }
		    BinaryStdOut.close();

		}
	}


    public static void main(String[] args) {
        if(args[0].equals("-")){
            char mode = args[1].charAt(0);
            if(mode == 'n' || mode == 'r' || mode == 'm'){
                compress(mode);
            }else{
                throw new IllegalArgumentException("Illegal command line argument");
            }
        }else if(args[0].equals("+")){
            expand();
        }
        else{
            throw new IllegalArgumentException("Illegal command line argument");
        }
    }
}
