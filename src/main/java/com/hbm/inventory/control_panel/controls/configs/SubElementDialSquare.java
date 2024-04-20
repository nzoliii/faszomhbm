package com.hbm.inventory.control_panel.controls.configs;

import com.hbm.inventory.control_panel.DataValue;
import com.hbm.inventory.control_panel.DataValueFloat;
import com.hbm.inventory.control_panel.DataValueString;
import com.hbm.inventory.control_panel.GuiControlEdit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

import java.util.HashMap;
import java.util.Map;

public class SubElementDialSquare extends SubElementBaseConfig {

    private String text;

    GuiTextField textField;

    public SubElementDialSquare(GuiControlEdit gui, Map<String, DataValue> map) {
        super(gui);
        this.text = map.get("label").toString();
    }

    @Override
    public Map<String, DataValue> getConfigs() {
        Map<String, DataValue> m = new HashMap<>();
        m.put("label", new DataValueString(text));
        return m;
    }

    @Override
    public void initGui() {
        int cX = gui.width/2;
        int cY = gui.height/2;

        textField = new GuiTextField(gui.currentButtonId(), Minecraft.getMinecraft().fontRenderer, cX-10, gui.getGuiTop()+70, 120, 20);
        textField.setText(text);

        super.initGui();
    }

    @Override
    protected void update() {
        super.update();
        textField.updateCursorCounter();
        text = textField.getText();
    }

    @Override
    protected void drawScreen() {
        textField.drawTextBox();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        this.textField.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        this.textField.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    public void enableButtons(boolean enable) {
        textField.setEnabled(enable);
        textField.setVisible(enable);
    }
}
