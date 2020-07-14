package lab.Utils;


import lab.BasicClasses.Album;
import lab.BasicClasses.MusicBand;
import lab.BasicClasses.MusicGenre;

import java.util.Arrays;

public class Validator {
    private static boolean checkGenre(String toContains) {
        return Arrays.stream(MusicGenre.values()).anyMatch((color) -> color.name().equals(toContains));
    }

    public static boolean validateMusicBand(MusicBand musicBand) {
        try {
            return (musicBand.getName() != null && !musicBand.getName().equals("")) &&
                    musicBand.getCoordinates().getX() > -687 && musicBand.getCoordinates().getY() != null &&
                    musicBand.getNumberOfParticipants() > 0 &&
                    musicBand.getCoordinates().getY() != null &&
                    musicBand.getGenre() != null &&
                    validateAlbum(musicBand.getBestAlbum());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validateAlbum(Album album) {
        return (album.getName() != null && !album.getName().equals("")) &&
                album.getSales() > 0 &&
                album.getLength() > 0 &&
                album.getTracks() > 0;
    }
}
