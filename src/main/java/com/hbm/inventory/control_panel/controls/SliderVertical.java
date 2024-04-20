package com.hbm.inventory.control_panel.controls;

import com.hbm.inventory.control_panel.*;
import com.hbm.inventory.control_panel.nodes.*;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.amlfrom1710.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SliderVertical extends Control {

    public SliderVertical(String name, ControlPanel panel) {
        super(name, panel);
        vars.put("value", new DataValueFloat(0));
    }

    @Override
    public ControlType getControlType() {
        return ControlType.SLIDER;
    }

    @Override
    public float[] getSize() {
        return new float[] {0.8125F, 2.3125F, .31F};
    }

    @Override
    public void render() {
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.ctrl_slider_vertical_tex);
        Tessellator tes = Tessellator.instance;
        IModelCustom model = getModel();

        int position = (int) Math.abs(getVar("value").getNumber()) % 6;

        tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        tes.setTranslation(posX, 0, posY);
        tes.setColorRGBA_F(1, 1, 1, 1);
        model.tessellatePart(tes, "base");
        tes.draw();

        GlStateManager.disableTexture2D();

        tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        tes.setTranslation(posX, 0, posY-(.3125F*position));
        tes.setColorRGBA_F(51/255F, 51/255F, 51/255F, 1);
        model.tessellatePart(tes, "slider");
        tes.draw();

        float lX = OpenGlHelper.lastBrightnessX;
        float lY = OpenGlHelper.lastBrightnessY;

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);

        for (int i=0; i<=position; i++) {
            tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
            tes.setTranslation(posX, 0, posY);
            tes.setColorRGBA_F(0, 1, 0, 1);
            model.tessellatePart(tes, "light"+i);
            tes.draw();
        }

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lX, lY);
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(GL11.GL_FLAT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IModelCustom getModel() {
        return ResourceManager.ctrl_slider_vertical;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getGuiTexture() {
        return ResourceManager.ctrl_slider_vertical_gui_tex;
    }

    @Override
    public List<String> getOutEvents() {
        return Collections.singletonList("ctrl_press");
    }

    @Override
    public void populateDefaultNodes(List<ControlEvent> receiveEvents) {
        NodeSystem ctrl_press = new NodeSystem(this);
        {
            Map<String, DataValue> vars = new HashMap<>(receiveEvents.get(0).vars);
            vars.put("from index", new DataValueFloat(0));
            NodeInput node0 = new NodeInput(170, 100, "Event Data").setVars(vars);
            ctrl_press.addNode(node0);
            NodeGetVar node1 = new NodeGetVar(170, 150, this).setData("value", false);
            ctrl_press.addNode(node1);
            NodeConditional node2 = new NodeConditional(230, 110);
            node2.inputs.get(0).setData(node0, 1, true);
            node2.inputs.get(1).setDefault(new DataValueFloat(-1));
            node2.inputs.get(2).setDefault(new DataValueFloat(1));
            ctrl_press.addNode(node2);
            NodeMath node3 = new NodeMath(290, 130).setData(NodeMath.Operation.ADD);
            node3.inputs.get(0).setData(node2, 0, true);
            node3.inputs.get(1).setData(node1, 0, true);
            ctrl_press.addNode(node3);
            NodeMath node4 = new NodeMath(350, 130).setData(NodeMath.Operation.CLAMP);
            node4.inputs.get(0).setData(node3, 0, true);
            node4.inputs.get(1).setDefault(new DataValueFloat(0));
            node4.inputs.get(2).setDefault(new DataValueFloat(5));
            ctrl_press.addNode(node4);
            NodeSetVar node5 = new NodeSetVar(410, 110, this).setData("value", false);
            node5.inputs.get(0).setData(node4, 0, true);
            ctrl_press.addNode(node5);
        }
        receiveNodeMap.put("ctrl_press", ctrl_press);
    }

    @Override
    public Control newControl(ControlPanel panel) {
        return new SliderVertical(name, panel);
    }

}
