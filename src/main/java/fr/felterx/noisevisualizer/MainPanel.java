package fr.felterx.noisevisualizer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainPanel extends JPanel {
    private static BufferedImage resultImage;

    private static float noiseScale = 64.0f;// 64
    private static int noiseOctaves = 4;
    private static float noisePersistance = 0.5f;
    private static float noiseLacunarity = 2.0f;

    private static long seed;

    private JPanel varsPanel;


    public MainPanel() {
        seed = (new Random()).nextLong();
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                generateImage();
            }
        }, 100, 100);

        this.setPreferredSize(new Dimension(1280, 720));
        this.setLayout(null);

        resultImage = new BufferedImage(1280 / 2, 720, BufferedImage.TYPE_INT_ARGB);

        varsPanel = new JPanel() {
            {
                this.setLayout(null);
                this.setBounds(0, 0, 1280 / 2, 720);
                this.setBorder(new LineBorder(Color.BLACK, 2));

                // ======================== \\

                JSlider scaleSlider = new JSlider();
                scaleSlider.setBounds(0, 20, 1280 / 2, 170);
                scaleSlider.setBackground(new Color(0, 0, 0, 0));
                scaleSlider.setMinimum(0);
                scaleSlider.setMaximum(9999);

                JLabel scaleValueLabel = new JLabel("null");
                scaleValueLabel.setBounds(20, 0, 1280 / 2, 170);
                scaleValueLabel.setBackground(new Color(0, 0, 0, 0));
                scaleValueLabel.setText("Noise scale: " + scaleSlider.getValue());

                scaleSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        noiseScale = scaleSlider.getValue() / (float) scaleSlider.getMaximum() * 256f;
                        scaleValueLabel.setText("Noise scale: " + noiseScale);
                    }
                });


                // ======================== \\

                JSlider octavesSlider = new JSlider();
                octavesSlider.setBounds(0, 20 + 170, 1280 / 2, 170);
                octavesSlider.setBackground(new Color(0, 0, 0, 0));
                octavesSlider.setMinimum(1);
                octavesSlider.setMaximum(6);

                JLabel octavesValueLabel = new JLabel("null");
                octavesValueLabel.setBounds(20, 170, 1280 / 2, 170);
                octavesValueLabel.setBackground(new Color(0, 0, 0, 0));
                octavesValueLabel.setText("Noise octaves: " + octavesSlider.getValue());

                octavesSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        noiseOctaves = octavesSlider.getValue();
                        octavesValueLabel.setText("Noise octaves: " + noiseOctaves);
                    }
                });

                // ======================== \\

                JSlider persistanceSlider = new JSlider();
                persistanceSlider.setBounds(0, 20 + 170 * 2, 1280 / 2, 170);
                persistanceSlider.setBackground(new Color(0, 0, 0, 0));
                persistanceSlider.setMinimum(0);
                persistanceSlider.setMaximum(9999);

                JLabel persistanceValueLabel = new JLabel("null");
                persistanceValueLabel.setBounds(20, 170 * 2, 1280 / 2, 170);
                persistanceValueLabel.setBackground(new Color(0, 0, 0, 0));
                persistanceValueLabel.setText("Noise persistance: " + persistanceSlider.getValue());

                persistanceSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        noisePersistance = persistanceSlider.getValue() / (float) persistanceSlider.getMaximum() * 2.5f;
                        persistanceValueLabel.setText("Noise persistance: " + noisePersistance);
                    }
                });


                // ======================== \\

                JSlider lacunaritySlider = new JSlider();
                lacunaritySlider.setBounds(0, 20 + 170 * 3, 1280 / 2, 170);
                lacunaritySlider.setBackground(new Color(0, 0, 0, 0));
                lacunaritySlider.setMinimum(0);
                lacunaritySlider.setMaximum(9999);

                JLabel lacunarityValueLabel = new JLabel("null");
                lacunarityValueLabel.setBounds(20, 170 * 3, 1280 / 2, 170);
                lacunarityValueLabel.setBackground(new Color(0, 0, 0, 0));
                lacunarityValueLabel.setText("Noise lacunarity: " + lacunaritySlider.getValue());

                lacunaritySlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        noiseLacunarity = lacunaritySlider.getValue() / (float) lacunaritySlider.getMaximum() * 16f;
                        lacunarityValueLabel.setText("Noise lacunarity: " + noiseLacunarity);
                    }
                });

                // ======================== \\

                add(scaleSlider);
                add(scaleValueLabel);

                add(octavesSlider);
                add(octavesValueLabel);

                add(persistanceSlider);
                add(persistanceValueLabel);

                add(lacunaritySlider);
                add(lacunarityValueLabel);
            }
        };

        add(varsPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(resultImage, 1280 / 2, 0, 1280 / 2, 720, null);
    }

    public static void generateImage() {
        new Thread(() -> {
            float[][] heights = new float[resultImage.getWidth()][resultImage.getHeight()];

            Random prng = new Random(seed);
            Vector2[] octavesOffsets = new Vector2[noiseOctaves];
            for (int i = 0; i < noiseOctaves; i++) {
                int offsetX = MathsUtil.getRandom(-100000, 100000, prng);
                int offsetY = MathsUtil.getRandom(-100000, 100000, prng);
                octavesOffsets[i] = new Vector2(offsetX, offsetY);
            }

            float maxNoiseHeight = Float.MIN_VALUE;
            float minNoiseHeight = Float.MAX_VALUE;

            for (int y = 0; y < resultImage.getHeight(); y++) {
                for (int x = 0; x < resultImage.getWidth(); x++) {

                    float amplitude = 1;
                    float frequency = 1f;
                    float noiseHeight = 0;

                    for (int i = 0; i < noiseOctaves; i++) {
                        float sampleX = x / noiseScale * frequency + octavesOffsets[i].x;
                        float sampleY = y / noiseScale * frequency + octavesOffsets[i].y;

                        float perlinValue = (float) Noise.noise(sampleX, sampleY);
                        noiseHeight += perlinValue * amplitude;
                        amplitude *= noisePersistance;
                        frequency *= noiseLacunarity;
                    }

                    if (noiseHeight > maxNoiseHeight)
                        maxNoiseHeight = noiseHeight;
                    else if (noiseHeight < minNoiseHeight)
                        minNoiseHeight = noiseHeight;

                    heights[x][y] = noiseHeight;
                }
            }


            for (int y = 0; y < resultImage.getHeight(); y++) {
                for (int x = 0; x < resultImage.getWidth(); x++) {
                    float v = (heights[x][y] - minNoiseHeight) / (maxNoiseHeight - minNoiseHeight);
                    Color color = new Color(v, v, v, 1);
                    resultImage.setRGB(x, y, color.getRGB());
                }
            }
        }).start();
    }
}
