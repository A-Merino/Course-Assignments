public final class SampleProgram {   
     /**
     * Test client - play an A major scale to standard audio.
     *
     * @param args the command-line arguments
     */
    /**
     * Test client - play an A major scale to standard audio.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        
        // 440 Hz for 1 sec
        double freq = 880.0;
         
        for (int i = 0; i <= MyAudio.SAMPLE_RATE; i++) {
            MyAudio.play(0.5 * Math.sin(2*Math.PI * freq * i / MyAudio.SAMPLE_RATE));
        }

        // need to call this in non-interactive stuff so the program doesn't terminate
        // until all the sound leaves the speaker.
        MyAudio.close(); 
    }
}