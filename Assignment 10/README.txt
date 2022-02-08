Running through the calgary, waterloo, and BooksAndHTML folderswith HuffMark, I noticed some trends.
Most notably, the calgary folder, the one filed with generic file types, had the most compression
at 43.239% compressed (3,251,493 bytes to 1,845,571 bytes), and the waterloo folder, filled with
.tif images had the least with 18.137% compression (12,466,304 bytes to 10,205,282 bytes). Along
with this, I noticed, as was stated in the specification, that when using SCF on small files, I
saw increased bytes in compressing the file. The timing data is also interesting as the folder
with the most compression, calgary, took the least amount of time (5.261 seconds. In contrast, 
the waterloo file, with the least amount of compression, took the most amount of time (28 seconds).
This is more than likely due to size and has nothing to do with the amount of compression. The books
folder, for example, was larger than waterloo (15,613,966 bytes) with more compression (33%), but it
is actually .5 seconds longer. As for compressing an already compressed file, it seemed to just 
reduce further compress it. I took the CIA Fact Book (3,497,369 bytes) and compressed it once to 
2,260,664 bytes and again to 2,240,008 bytes (note: the compression percent is far lower than the
first time here). Decompressing it twice, I was able to fully recover the file to its original 
3,497,369 bytes. Below, I provided my test runs for the three large files for reference. 

compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\bib.hf
bib from	 111261 to	 73795 in	 0.232
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\book1.hf
book1 from	 768771 to	 439409 in	 1.235
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\book2.hf
book2 from	 610856 to	 369335 in	 1.033
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\geo.hf
geo from	 102400 to	 73592 in	 0.211
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\news.hf
news from	 377109 to	 247428 in	 0.689
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\obj1.hf
obj1 from	 21504 to	 17085 in	 0.052
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\obj2.hf
obj2 from	 246814 to	 195131 in	 0.547
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\paper1.hf
paper1 from	 53161 to	 34371 in	 0.101
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\paper2.hf
paper2 from	 82199 to	 48649 in	 0.141
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\paper3.hf
paper3 from	 46526 to	 28309 in	 0.083
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\paper4.hf
paper4 from	 13286 to	 8894 in	 0.030
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\paper5.hf
paper5 from	 11954 to	 8465 in	 0.028
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\paper6.hf
paper6 from	 38105 to	 25057 in	 0.074
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\pic.hf
pic from	 513216 to	 107586 in	 0.313
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\progc.hf
progc from	 39611 to	 26948 in	 0.080
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\progl.hf
progl from	 71646 to	 44017 in	 0.128
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\progp.hf
progp from	 49379 to	 31248 in	 0.091
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\calgary\trans.hf
trans from	 93695 to	 66252 in	 0.193
--------
total bytes read: 3251493
total compressed bytes 1845571
total percent compression 43.239
compression time: 5.261

compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\waterloo\clegg.tif.hf
clegg.tif from	 2149096 to	 2034595 in	 5.655
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\waterloo\frymire.tif.hf
frymire.tif from	 3706306 to	 2188593 in	 5.972
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\waterloo\lena.tif.hf
lena.tif from	 786568 to	 766146 in	 2.105
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\waterloo\monarch.tif.hf
monarch.tif from	 1179784 to	 1109973 in	 3.045
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\waterloo\peppers.tif.hf
peppers.tif from	 786568 to	 756968 in	 2.070
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\waterloo\sail.tif.hf
sail.tif from	 1179784 to	 1085501 in	 2.992
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\waterloo\serrano.tif.hf
serrano.tif from	 1498414 to	 1127645 in	 3.054
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\waterloo\tulips.tif.hf
tulips.tif from	 1179784 to	 1135861 in	 3.124
--------
total bytes read: 12466304
total compressed bytes 10205282
total percent compression 18.137
compression time: 28.017

compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\A7_Recursion.html.hf
A7_Recursion.html from	 41163 to	 26189 in	 0.090
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\A7_Recursion.html.hf.unhf.hf
A7_Recursion.html.hf.unhf from	 26189 to	 26340 in	 0.081
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\A7_Recursion.html.hf.unhf.unhf.hf
A7_Recursion.html.hf.unhf.unhf from	 41163 to	 26189 in	 0.078
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\CiaFactBook2000.txt.hf
CiaFactBook2000.txt from	 3497369 to	 2260664 in	 6.265
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\CiaFactBook2000.txt.hf.unhf.hf
CiaFactBook2000.txt.hf.unhf from	 2260664 to	 2240008 in	 6.094
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\CiaFactBook2000.txt.hf.unhf.unhf.hf
CiaFactBook2000.txt.hf.unhf.unhf from	 3497369 to	 2260664 in	 6.199
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\jnglb10.txt.hf
jnglb10.txt from	 292059 to	 168618 in	 0.463
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\kjv10.txt.hf
kjv10.txt from	 4345020 to	 2489768 in	 6.836
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\melville.txt.hf
melville.txt from	 82140 to	 47364 in	 0.132
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\quotes.htm.hf
quotes.htm from	 61563 to	 38423 in	 0.106
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\rawMovieGross.txt.hf
rawMovieGross.txt from	 117272 to	 53833 in	 0.149
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\revDictionary.txt.hf
revDictionary.txt from	 1130523 to	 611618 in	 1.671
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\syllabus.htm.hf
syllabus.htm from	 33273 to	 21342 in	 0.059
compressing to: C:\Users\Tyler\eclipse-workspace\Huffman\BooksAndHTML\ThroughTheLookingGlass.txt.hf
ThroughTheLookingGlass.txt from	 188199 to	 110293 in	 0.303
--------
total bytes read: 15613966
total compressed bytes 10381313
total percent compression 33.513
compression time: 28.526