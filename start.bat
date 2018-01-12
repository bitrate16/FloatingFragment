::

set /p frame_size=<frame_size.txt
set /p frame_delay=<frame_delay.txt
set /p source_location=<source_location.txt
set /p source_type=<source_type.txt

java -jar -Dframe_delay=%frame_delay% -Dsource_location=%source_location% -Dsource_type=%source_type% -Dframe_size=%frame_size% FloatPony.jar
