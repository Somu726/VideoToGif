import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class VideoToGifConverter {

    private static final String FFmpeg_PATH = "C:\\ffmpeg\\ffmpeg-2024-06-16-git-fcf72966a5-full_build\\bin\\ffmpeg.exe"; // Adjust the path to the FFmpeg executable

    public static void main(String[] args) {
        String inputVideoPath = "C:\\videos\\video.mp4"; 
        // Input video file path
        String outputGifPath = "C:\\gif\\output5.gif"; // Output GIF file path
        int startTime = 78; // Start time in seconds
        int duration = 3; // Duration of the GIF in seconds

        try {
            convertVideoToGif(inputVideoPath, outputGifPath, startTime, duration);
            System.out.println("Conversion complete!");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void convertVideoToGif(String inputVideoPath, String outputGifPath, int startTime, int duration) throws IOException, InterruptedException {
        File inputFile = new File(inputVideoPath);
        File outputFile = new File(outputGifPath);

        if (!inputFile.exists()) {
            throw new IOException("Input video file does not exist: " + inputVideoPath);
        }

        if (outputFile.exists()) {
            FileUtils.forceDelete(outputFile);
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
            FFmpeg_PATH,
            "-ss", String.valueOf(startTime),
            "-t", String.valueOf(duration),
            "-i", inputVideoPath,
            "-vf", "fps=30,scale=480:-1:flags=lanczos",
            "-c:v", "gif",
            outputGifPath
        );

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("FFmpeg process exited with code " + exitCode);
        }
    }
}