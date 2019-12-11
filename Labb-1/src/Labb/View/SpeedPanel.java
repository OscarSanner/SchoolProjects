package Labb.View;

import Labb.IObserver;
import Labb.Model.IDrawable;
import Labb.Model.Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpeedPanel extends JPanel implements IObserver {

    Model model;
    List<JLabel> labels;

    public SpeedPanel(Model model){
        this.model = model;
        labels = new ArrayList<>();
        setLabels(labels);
        displayLabels();
        model.attach(this);
    }

    @Override
    public void observerUpdate() {
        labels = overrideRePaint();
        displayLabels();
        this.repaint();
    }

    List<JLabel> overrideRePaint(){
        List<JLabel> freshLabelList = new ArrayList<>();
        setLabels(freshLabelList);
        return freshLabelList;
    }

    private void displayLabels() {
        for(JLabel label : labels){
            this.add(label);
        }
    }

    private void setLabels(List<JLabel> labels) {
        for(IDrawable drawable : model.getDrawables()){
            labels.add(new JLabel(textBuilder(drawable)));
        }
    }

    private String textBuilder(IDrawable drawable) {
        StringBuilder sb = new StringBuilder();
        sb.append("<" + drawable.getModelName() + ">:" + drawable.getCurrentSpeed());
        return sb.toString();
    }
}
