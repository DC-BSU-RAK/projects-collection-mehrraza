package com.example.colormixapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity – the Colour Mixer.
 *
 * The user picks two colours (each defined by R/G/B sliders),
 * then presses "Mix!" to see the blended result displayed as a
 * large colour swatch with its hex code. A floating info button
 * opens a modal sheet explaining how the app works.
 */
public class MainActivity extends AppCompatActivity {

    // ── Colour A widgets ──────────────────────────────────────────────────────
    private SeekBar sbAR, sbAG, sbAB;
    private View swatchA;
    private TextView tvHexA;

    // ── Colour B widgets ──────────────────────────────────────────────────────
    private SeekBar sbBR, sbBG, sbBB;
    private View swatchB;
    private TextView tvHexB;

    // ── Result widgets ────────────────────────────────────────────────────────
    private View swatchResult;
    private TextView tvHexResult;
    private TextView tvMixLabel;

    // ── Buttons ───────────────────────────────────────────────────────────────
    private Button btnMix;
    private Button btnInfo;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setupSliderListeners();
        setupButtons();

        // Set initial swatches to reflect default slider positions
        updateSwatchA();
        updateSwatchB();
    }

    // ── View Binding ──────────────────────────────────────────────────────────

    private void bindViews() {
        sbAR = findViewById(R.id.sbAR);
        sbAG = findViewById(R.id.sbAG);
        sbAB = findViewById(R.id.sbAB);
        swatchA = findViewById(R.id.swatchA);
        tvHexA = findViewById(R.id.tvHexA);

        sbBR = findViewById(R.id.sbBR);
        sbBG = findViewById(R.id.sbBG);
        sbBB = findViewById(R.id.sbBB);
        swatchB = findViewById(R.id.swatchB);
        tvHexB = findViewById(R.id.tvHexB);

        swatchResult = findViewById(R.id.swatchResult);
        tvHexResult = findViewById(R.id.tvHexResult);
        tvMixLabel = findViewById(R.id.tvMixLabel);

        btnMix = findViewById(R.id.btnMix);
        btnInfo = findViewById(R.id.btnInfo);
        btnReset = findViewById(R.id.btnReset);
    }

    // ── Slider Listeners ──────────────────────────────────────────────────────

    private void setupSliderListeners() {
        SeekBar.OnSeekBarChangeListener listenerA = new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar s, int p, boolean u) { updateSwatchA(); }
            @Override public void onStartTrackingTouch(SeekBar s) {}
            @Override public void onStopTrackingTouch(SeekBar s) {}
        };

        SeekBar.OnSeekBarChangeListener listenerB = new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar s, int p, boolean u) { updateSwatchB(); }
            @Override public void onStartTrackingTouch(SeekBar s) {}
            @Override public void onStopTrackingTouch(SeekBar s) {}
        };

        sbAR.setOnSeekBarChangeListener(listenerA);
        sbAG.setOnSeekBarChangeListener(listenerA);
        sbAB.setOnSeekBarChangeListener(listenerA);

        sbBR.setOnSeekBarChangeListener(listenerB);
        sbBG.setOnSeekBarChangeListener(listenerB);
        sbBB.setOnSeekBarChangeListener(listenerB);
    }

    // ── Button Setup ──────────────────────────────────────────────────────────

    private void setupButtons() {
        btnMix.setOnClickListener(v -> mixColours());
        btnInfo.setOnClickListener(v -> showInfoModal());
        btnReset.setOnClickListener(v -> resetAll());
    }

    // ── Colour Logic ──────────────────────────────────────────────────────────

    /** Reads Colour A sliders and refreshes the swatch + hex label. */
    private void updateSwatchA() {
        int color = colorFromSliders(sbAR, sbAG, sbAB);
        swatchA.setBackgroundColor(color);
        tvHexA.setText(colorToHex(color));
    }

    /** Reads Colour B sliders and refreshes the swatch + hex label. */
    private void updateSwatchB() {
        int color = colorFromSliders(sbBR, sbBG, sbBB);
        swatchB.setBackgroundColor(color);
        tvHexB.setText(colorToHex(color));
    }

    /**
     * Averages Colour A and Colour B channel-by-channel to produce a
     * blended colour, then displays it in the result swatch.
     */
    private void mixColours() {
        int colA = colorFromSliders(sbAR, sbAG, sbAB);
        int colB = colorFromSliders(sbBR, sbBG, sbBB);

        int rMix = (Color.red(colA)   + Color.red(colB))   / 2;
        int gMix = (Color.green(colA) + Color.green(colB)) / 2;
        int bMix = (Color.blue(colA)  + Color.blue(colB))  / 2;

        int mixed = Color.rgb(rMix, gMix, bMix);
        swatchResult.setBackgroundColor(mixed);
        tvHexResult.setText(colorToHex(mixed));
        tvMixLabel.setText(R.string.label_mix_result);

        // Animate the result swatch (quick scale pop)
        swatchResult.animate()
                .scaleX(1.05f).scaleY(1.05f).setDuration(120)
                .withEndAction(() ->
                        swatchResult.animate().scaleX(1f).scaleY(1f).setDuration(120).start())
                .start();
    }

    /** Resets all sliders to zero and clears the result. */
    private void resetAll() {
        sbAR.setProgress(0); sbAG.setProgress(0); sbAB.setProgress(0);
        sbBR.setProgress(0); sbBG.setProgress(0); sbBB.setProgress(0);
        swatchResult.setBackgroundColor(Color.LTGRAY);
        tvHexResult.setText(R.string.placeholder_hex);
        tvMixLabel.setText(R.string.label_mix_ready);
    }

    // ── Modal Info Sheet ──────────────────────────────────────────────────────

    /**
     * Inflates a custom layout and displays it as an AlertDialog modal,
     * satisfying the brief's requirement for a pop-up informational view.
     */
    private void showInfoModal() {
        View modalView = LayoutInflater.from(this)
                .inflate(R.layout.modal_info, null);

        new AlertDialog.Builder(this)
                .setView(modalView)
                .setCancelable(true)
                .create()
                .show();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static int colorFromSliders(SeekBar r, SeekBar g, SeekBar b) {
        return Color.rgb(r.getProgress(), g.getProgress(), b.getProgress());
    }

    private static String colorToHex(int color) {
        return String.format("#%02X%02X%02X",
                Color.red(color), Color.green(color), Color.blue(color));
    }
}
