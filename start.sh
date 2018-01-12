#!/bin/bash
frame_size=$(<frame_size.txt)
frame_delay=$(<frame_delay.txt)
source_location=$(<source_location.txt)
source_type=$(<source_type.txt)

java -jar -Dframe_size="$frame_size" -Dframe_delay="$frame_delay" -Dsource_location="$source_location" -Dsource_type="$source_type" FloatPony.jar
