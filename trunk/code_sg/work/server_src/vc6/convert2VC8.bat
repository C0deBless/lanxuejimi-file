cd ..
rename vc8 vc8bak
mkdir vc8
copy vc6\vc6.dsp vc8
copy vc6\vc6.dsw vc8
del ServerMain\*.vcproj.*
del GameWorld\*.vcproj.*
del public\ServerTBase\*.vcproj.*
del public\DBMysql\*.vcproj.*
del public\DataPacket\*.vcproj.*
del public\common\*.vcproj.*
del public\randomc\*.vcproj.*

pause
