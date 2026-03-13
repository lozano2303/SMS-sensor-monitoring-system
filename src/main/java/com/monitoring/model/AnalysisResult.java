package com.monitoring.model;

public class AnalysisResult {
    private final boolean isCritical;
    private final String message;
    private final double threshold;

    public AnalysisResult(boolean isCritical, String message, double threshold) {
        this.isCritical = isCritical;
        this.message = message;
        this.threshold = threshold;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public String getMessage() {
        return message;
    }

    public double getThreshold() {
        return threshold;
    }

    @Override
    public String toString() {
        return "Analisis: " + message + " (Umbral: " + threshold + ")";
    }
}
