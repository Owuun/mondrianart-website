/* Owen Wren 
    CSE 123
    C2: Mondrian Art
    5/13/2025
*/
import java.util.*;
import java.awt.*;

/* This program creates a randomly generated piece of mondrian art. It has two options,
    a basic mondrian art piece that generates completely random colors from 4 colors, and
    a complex mondrian art piece that weighs the generation of the 4 colors based on the location
    that part of the piece is in (Top left: White, Bottom Left: Red, Top Right: Yellow,
    Bottom Right: Cyan).
*/
public class Mondrian {

    private static final Color[] COLORS = {Color.WHITE, Color.RED, Color.CYAN, Color.YELLOW};
    private static final Random RAND = new Random();
    private static final int MARGIN = 10;
    private static final int MIN_LENGTH = 300;

    // B: This method is for the basic mondrian painting option, making it so the extension
    // doesn't run and then calling a method to paint and divide the painting.
    // E: Throws a new IllegalArgumentException if pixels is null, or if the width or length for
    // the art piece passed in is less than 300
    // R: No returns 
    // P: Accepts a 2D array called pixels that represents the pixels of the painting
    public void paintBasicMondrian(Color[][] pixels) {
        if (pixels == null || pixels.length < MIN_LENGTH || pixels[0].length < MIN_LENGTH) {
            throw new IllegalArgumentException();
        }
        paint(pixels, 0, pixels[0].length, 0, pixels.length, false);
    }

    // B: This method divides and ultimately paints each part of the painting. Each part of the
    // painting is divided based on their size and a random number. If the complex option is chosen
    // then the code will paint the sections according the the complex version, not the basic.
    // E: No exceptions
    // R: No returns 
    // P: Accepts a 2D array called pixels that represents the pixels of the painting, 4 ints that
    // represent x and y position of the regions corners, and 2 ints that represent the width and
    // height of the whole canvas
    private void paint(Color[][] pixels, int x1, int x2, int y1, 
            int y2, boolean extension) {
        int regionWidth = x2 - x1;
        int regionHeight = y2 - y1;
        if (regionHeight >= pixels.length / 4 && regionWidth >= pixels[0].length / 4) {
            int midPointX = x1 + MARGIN + RAND.nextInt(regionWidth - MARGIN * 2);
            int midPointY = y1 + MARGIN + RAND.nextInt(regionHeight - MARGIN * 2);
            paint(pixels, x1, midPointX, y1, midPointY, extension);
            paint(pixels, midPointX, x2, y1, midPointY, extension);
            paint(pixels, x1, midPointX, midPointY, y2, extension);
            paint(pixels, midPointX, x2, midPointY, y2, extension);
        } else if (regionHeight >= pixels.length / 4 && regionHeight > MARGIN * 2) {
            int midPointY = y1 + MARGIN + RAND.nextInt(regionHeight - MARGIN * 2);
            paint(pixels, x1, x2, y1, midPointY, extension);
            paint(pixels, x1, x2, midPointY, y2, extension);
        } else if (regionWidth >= pixels[0].length / 4 && regionWidth > MARGIN * 2) {
            int midPointX = x1 + MARGIN + RAND.nextInt(regionWidth - MARGIN * 2);
            paint(pixels, x1, midPointX, y1, y2, extension);
            paint(pixels, midPointX, x2, y1, y2, extension);
        } else {
            if (extension){
                fill(pixels, x1 + 1, x2 - 1, y1 + 1, y2 - 1, 
                        weightedRandomColor(x1, y1, pixels[0].length, pixels.length));
            } else {
                fill(pixels, x1 + 1, x2 - 1, y1 + 1, y2 - 1, COLORS[RAND.nextInt(COLORS.length)]);
            }
        }
    }

    // B: This method chooses a random color from 4 colors, Cyan, Red, Yellow, and White
    // E: No exceptions
    // R: Returns a Color that will be used for the color of the region of the painting that 
    // is being filled
    // P: No parameters

    // B: This method chooses a random color from 4 colors, Cyan, Red, Yellow, and White however,
    // depending on the location of the region. Top right is yellow, top left is white, bottom left
    // is red, bottom right is cyan.
    // E: No exceptions
    // R: Returns a Color that will be used for the color of the region of the painting that 
    // is being filled
    // P: Accepts the far left x and lowest y coordinate of the region, and the height and width
    // of the canvas
    private Color weightedRandomColor(int x1, int y1, int canvasWidth, int canvasHeight){
        if (x1 + MARGIN < canvasWidth / 2 && y1 + MARGIN < canvasHeight / 2){
            Color[] colors = {Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.RED, 
                    Color.CYAN, Color.YELLOW};
            return colors[RAND.nextInt(colors.length)];
        } else if (x1 + MARGIN < canvasWidth / MARGIN && y1 > canvasHeight /2){
            Color[] colors = {Color.WHITE, Color.RED, Color.RED, Color.RED, Color.RED, 
                    Color.CYAN, Color.YELLOW};
            return colors[RAND.nextInt(colors.length)];
        } else if (x1 > canvasWidth / 2 && y1 > canvasHeight /2){
            Color[] colors = {Color.WHITE, Color.RED, Color.CYAN, Color.CYAN, Color.CYAN, 
                    Color.CYAN, Color.YELLOW};
            return colors[RAND.nextInt(colors.length)];
        } else {
            Color[] colors = {Color.WHITE, Color.RED, Color.CYAN, Color.YELLOW, Color.YELLOW, 
                    Color.YELLOW, Color.YELLOW};
            return colors[RAND.nextInt(colors.length)];
        }
    }

    // B: This method fills the pixels in a region with a passed in color
    // E: No excpetion
    // R: No returns
    // P: Accepts a 2D array called pixels that represents the pixels of the painting, 4 ints that
    // represent x and y position of the regions corners, and a Color for what color is going to
    // be filled in that region
    private void fill(Color[][] pixels, int x1, int x2, int y1, int y2, Color color){
        for (int i = x1; i < x2; i++){
            for (int j = y1; j < y2; j++){
                if (i >= 0 && i < pixels[0].length && j >= 0 && j < pixels.length) {
                    pixels[j][i] = color;
                }
            }
        }
    }

    // B: This method is for the complex mondrian option which does everything the same as the
    // basic option, but, the colors are weighted for the region that they are in. Meaning, 
    // in certain regions they're more likely to be certain colors
    // E: Throws a new IllegalArgumentException if pixels is null, or if the width or length for
    // the art piece passed in is less than 300
    // R: No returns
    // P: Accepts a 2D array called pixels that represents the pixels of the painting
    public void paintComplexMondrian(Color[][] pixels){
        if (pixels == null || pixels[0].length < MIN_LENGTH || pixels.length < MIN_LENGTH){
            throw new IllegalArgumentException();
        }
        paint(pixels, 0, pixels[0].length, 0, pixels.length, true);
    }

}
