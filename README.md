# LZW Compression
LZW Compression with a variable width codewords from 9-16 bits, as well as 3 different options for once the codebook is filled up (modes listed below).

## Compression Modes
MyLZW has 3 modes for compressing
* "n" - Do nothing: When the codebook fills up continue to use it
* "r" - Reset: When the codebook fills reset it back to the initial state so that new codewords can be added
* "m" - Moniter: When the codebook fills up moniter the compression ratio. If the compression ratio degrades by more than 1.1 from the point when the last codeword was added then the dictionayr is reset back to its initial state. Compression ratio is defined as the size of the uncompressed data that has been processed/generated so far divided by the size of the compressed data generated/processed so far (for compression/expansion, respectively). 

## Compile
Compile with ``javac MyLZW.java``

## Running
* Compress files in do nothing mode with ``java MyLZW - n < uncompressed_file > compressed_file`` 
* Compress files in reset mode with ``java MyLZW - r < uncompressed_file > compressed_file``
* Compress files in moniter mode with ``java MyLZW - m < uncompressed_file > compressed_file``
* Uncompress files from any compression mode with ``java MyLZW + < compressed_file > uncompressed_file``