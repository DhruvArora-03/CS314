Assignment 10 - Huffman Encoding

Authors: Rohit Anantha and Dhruv Arora

Write up will be an analysis of your experiments.

Books and HTML - Average percent compression: 40%

Waterloo - Average percent compression: 18%

Calgary - Average percent compression: 43%

What kinds of files lead to lots of compressions?

Text files are good to compress because the ASCII components range from a smaller subset of 256. Likely, with 26 characters for lowercase, 26 for uppercase, and assuming 10 for special characters, only around 60 of the values are ever used, meaning that the Huffman Tree will likely find smaller codes for most if not all the values. Therefore, the size of the compressed file will likely always have more compression and less space.

What kind of files had little or no compression?

Pictures seem to have little to no compression, likely because each individual pixel in the file is an RBGA pixel, having 8 bits of data for Red, Green, Blue, and the Alpha, all ranging from 0 - 255. Ergo, all the different values between 0 and 255 will likely be represented at least once. Ergo, the Huffman Tree will be near complete, having large codes for most if not all values. Some of the more common representations may occur closer to the top of the tree, but since the Huffman Tree is binary, it is likely that even some of the most common 8-bit ints would still not save that much space in the codes. Additionally, in order to have 256 leaves, there will be more internal leaves, meaning that some codes could be even longer than 8 bits. Overall, the Huffman style of encoding does not help with images.

What happens when you try and compress a Huffman code file?

Trying to compress a Huffman code file results in usually around 1.0 ratio between the original and prior. This is because the compressed file is essentially randomly generated, since the codes may be less than 8 bits, resulting in 8-bit sequences that are pseudorandom. Therefore, any number between 0 and 255 has an equal chance roughly of showing up, meaning that trying to compress based on frequencies is not going to work.
