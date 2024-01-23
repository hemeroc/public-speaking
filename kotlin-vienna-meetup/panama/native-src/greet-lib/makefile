CC = gcc
CFLAGS = -Wall -Wextra
LIBRARY_NAME = libgreet

SRC_DIR = src
TARGET_DIR = target

UNAME := $(shell uname -s)
ifeq ($(UNAME), Darwin)
    LIBRARY_EXTENSION = dylib
else
    LIBRARY_EXTENSION = so
endif

all: $(TARGET_DIR)/$(LIBRARY_NAME).$(LIBRARY_EXTENSION)

$(TARGET_DIR)/$(LIBRARY_NAME).$(LIBRARY_EXTENSION): $(TARGET_DIR)/greet.o
	$(CC) -shared -o $@ $^

$(TARGET_DIR)/greet.o: $(SRC_DIR)/greet.c $(SRC_DIR)/greet.h
	mkdir -p $(TARGET_DIR)
	$(CC) $(CFLAGS) -c -fPIC $< -o $@

clean:
	rm -rf $(TARGET_DIR)