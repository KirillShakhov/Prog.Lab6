package lab.BasicClasses;

import java.io.Serializable;


/** Музыкальные жанры*/
public enum MusicGenre implements Comparable<MusicGenre>, Serializable {
    PSYCHEDELIC_ROCK,
    RAP,
    POP,
    POST_ROCK,
    POST_PUNK
}