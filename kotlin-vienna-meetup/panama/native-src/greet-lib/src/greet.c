#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "greet.h"

char* greet(const char* name) {
    char* greeting = (char*)malloc(strlen("Hello ") + strlen(name) + 2);
    if (greeting != NULL) {
        strcpy(greeting, "Hello ");
        strcat(greeting, name);
        strcat(greeting, "!");
    }
    return greeting;
}
