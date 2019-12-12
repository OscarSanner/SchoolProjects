package Labb.View;

import Labb.IObserver;
import Labb.Model.IDrawable;
import Labb.Model.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpeedPanel extends JPanel implements IObserver {

    Model model;
    List<JLabel> labels;

    public SpeedPanel(Model model){
        this.model = model;
        labels = new ArrayList<>();
        this.repaint();
        model.attach(this);
    }

    @Override
    public void observerUpdate() {
        this.repaint();
    }

    private void compareAndAdjust() {
        if (model.getDrawables().size() != labels.size()){
            this.removeAll();
            labels.clear();
            setLabels(labels);
            displayLabels();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int vehicleCounter = 0;

        compareAndAdjust();

        for (JLabel label : labels) {
            label.setText(textBuilder(model.getDrawables().get(vehicleCounter)));
            vehicleCounter++;
        }
    }

    private void displayLabels() {
        for(JLabel label : labels){
            this.add(label);
        }
    }

    private void setLabels(List<JLabel> labels) {
        for(IDrawable drawable : model.getDrawables()){
            labels.add(new JLabel());
        }
    }

    private String textBuilder(IDrawable drawable) {
        StringBuilder sb = new StringBuilder();
        sb.append("<" + drawable.getModelName() + ">:" + drawable.getCurrentSpeed());
        return sb.toString();
    }
}
