package com.hbm.physics;

import javax.vecmath.Matrix3f;

import org.lwjgl.opengl.GL11;

import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.util.Triangle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class ConvexMeshCollider extends Collider {

	public Triangle[] triangles;
	public float[] vertices;
	public int[] indices;
	public AxisAlignedBB localBox;
	
	private ConvexMeshCollider(){
	}
	
	public ConvexMeshCollider(Triangle[] triangles){
		int[] indc = new int[triangles.length*3];
		float[] verts = new float[triangles.length*9];
		for(int i = 0; i < triangles.length; i ++){
			indc[i*3+0] = i*9+0;
			indc[i*3+1] = i*9+3;
			indc[i*3+2] = i*9+6;
			verts[i*9+0] = (float) triangles[i].p1.pos.x;
			verts[i*9+1] = (float) triangles[i].p1.pos.y;
			verts[i*9+2] = (float) triangles[i].p1.pos.z;
			verts[i*9+3] = (float) triangles[i].p2.pos.x;
			verts[i*9+4] = (float) triangles[i].p2.pos.y;
			verts[i*9+5] = (float) triangles[i].p2.pos.z;
			verts[i*9+6] = (float) triangles[i].p3.pos.x;
			verts[i*9+7] = (float) triangles[i].p3.pos.y;
			verts[i*9+8] = (float) triangles[i].p3.pos.z;
		}
		fromData(indc, verts);
	}
	
	public ConvexMeshCollider(Triangle[] triangles, float density){
		this(triangles);
		//Calculate inertia, mass, etc
		this.localCentroid = computeCenterOfMass();
		this.mass = computeVolume()*density;
		this.localInertiaTensor = computeInertia(localCentroid, mass);
	}

	public ConvexMeshCollider(int[] indices, float[] vertices, float density) {
		fromData(indices, vertices, density);
	}
	
	public ConvexMeshCollider(int[] indices, float[] vertices) {
		fromData(indices, vertices);
	}
	
	public void fromData(int[] indices, float[] vertices, float density){
		fromData(indices, vertices);
		
		//Calculate inertia, mass, etc
		this.localCentroid = computeCenterOfMass();
		this.mass = computeVolume()*density;
		this.localInertiaTensor = computeInertia(localCentroid, mass);
	}
	
	public void fromData(int[] indices, float[] vertices){
		this.indices = indices;
		this.vertices = vertices;
		triangles = new Triangle[indices.length/3];
		for(int i = 0; i < indices.length; i += 3){
			Vec3d p1 = new Vec3d(vertices[indices[i+0]*3+0], vertices[indices[i+0]*3+1], vertices[indices[i+0]*3+2]);
			Vec3d p2 = new Vec3d(vertices[indices[i+1]*3+0], vertices[indices[i+1]*3+1], vertices[indices[i+1]*3+2]);
			Vec3d p3 = new Vec3d(vertices[indices[i+2]*3+0], vertices[indices[i+2]*3+1], vertices[indices[i+2]*3+2]);
			triangles[i/3] = new Triangle(p1, p2, p3);
		}
		double maxX = support(RigidBody.cardinals[0]).xCoord;
		double maxY = support(RigidBody.cardinals[1]).yCoord;
		double maxZ = support(RigidBody.cardinals[2]).zCoord;
		double minX = support(RigidBody.cardinals[3]).xCoord;
		double minY = support(RigidBody.cardinals[4]).yCoord;
		double minZ = support(RigidBody.cardinals[5]).zCoord;
		this.localBox = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	//The three methods below are from this site.
	//http://melax.github.io/volint.html
	
	private float computeVolume(){
		float vol = 0;
		for(Triangle t : triangles){
			vol += new Matrix3f((float)t.p1.pos.x, (float)t.p1.pos.y, (float)t.p1.pos.z, (float)t.p2.pos.x, (float)t.p2.pos.y, (float)t.p2.pos.z, (float)t.p3.pos.x, (float)t.p3.pos.y, (float)t.p3.pos.z).determinant();
		}
		return vol/6F;
	}
	
	private Vec3 computeCenterOfMass(){
		Vec3 center = new Vec3(0, 0, 0);
		float volume = 0;
		for(Triangle t : triangles){
			Matrix3f mat = new Matrix3f((float)t.p1.pos.x, (float)t.p1.pos.y, (float)t.p1.pos.z, (float)t.p2.pos.x, (float)t.p2.pos.y, (float)t.p2.pos.z, (float)t.p3.pos.x, (float)t.p3.pos.y, (float)t.p3.pos.z);
			float vol = mat.determinant();
			center.xCoord += vol*(mat.m00+mat.m10+mat.m20);
			center.yCoord += vol*(mat.m01+mat.m11+mat.m21);
			center.zCoord += vol*(mat.m02+mat.m12+mat.m22);
			volume += vol;
		}
		center.xCoord /= volume*4F;
		center.yCoord /= volume*4F;
		center.zCoord /= volume*4F;
		return center;
	}
	
	private Matrix3f computeInertia(Vec3 com, float mass){
		float volume = 0;
		Vec3 diag = new Vec3(0, 0, 0);
		Vec3 offd = new Vec3(0, 0, 0);
		for(Triangle t : triangles){
			Matrix3f mat = new Matrix3f((float)(t.p1.pos.x-com.xCoord), (float)(t.p1.pos.y-com.yCoord), (float)(t.p1.pos.z-com.zCoord), 
					(float)(t.p2.pos.x-com.xCoord), (float)(t.p2.pos.y-com.yCoord), (float)(t.p2.pos.z-com.zCoord), 
							(float)(t.p3.pos.x-com.xCoord), (float)(t.p3.pos.y-com.yCoord), (float)(t.p3.pos.z-com.zCoord));
			float d = mat.determinant();
			volume += d;
			
			for(int j = 0; j < 3; j ++){
				int j1 = (j+1)%3;
				int j2 = (j+2)%3;
				setVal(diag, j, val(diag, j) + 
						(mat.getElement(0, j)*mat.getElement(1, j) + mat.getElement(1, j)*mat.getElement(2, j) + mat.getElement(2, j)*mat.getElement(0, j) +
						mat.getElement(0, j)*mat.getElement(0, j) + mat.getElement(1, j)*mat.getElement(1, j) + mat.getElement(2, j)*mat.getElement(2, j))*d);
				setVal(offd, j, val(offd, j) +
						(mat.getElement(0, j1)*mat.getElement(1, j2) + mat.getElement(1, j1)*mat.getElement(2, j2) + mat.getElement(2, j1)*mat.getElement(0, j2) + 
						mat.getElement(0, j1)*mat.getElement(2, j2) + mat.getElement(1, j1)*mat.getElement(0, j2) + mat.getElement(2, j1)*mat.getElement(1, j2) +
						mat.getElement(0, j1)*mat.getElement(0, j2) + mat.getElement(1, j1)*mat.getElement(1, j2) + mat.getElement(2, j1)*mat.getElement(2, j2))*d);
			}
		}
		float volume2 = volume*(60F/6F);
		diag.xCoord /= volume2;
		diag.yCoord /= volume2;
		diag.zCoord /= volume2;
		volume2 = volume*(120F/6F);
		offd.xCoord /= volume2;
		offd.yCoord /= volume2;
		offd.zCoord /= volume2;
		diag = diag.mult(mass);
		offd = offd.mult(mass);
		return new Matrix3f(
				(float)(diag.yCoord+diag.zCoord), (float)-offd.zCoord, (float)-offd.yCoord,
				(float)-offd.zCoord, (float)(diag.xCoord+diag.zCoord), (float)-offd.xCoord,
				(float)-offd.yCoord, (float)-offd.xCoord, (float)(diag.xCoord+diag.yCoord));
	}
	
	private static void setVal(Vec3 vec, int idx, double val){
		switch(idx){
		case 0:
			vec.xCoord = val; return;
		case 1:
			vec.yCoord = val; return;
		case 2:
			vec.zCoord = val; return;
		}
	}
	
	private static double val(Vec3 vec, int idx){
		switch(idx){
		case 0:
			return vec.xCoord;
		case 1:
			return vec.yCoord;
		case 2:
			return vec.zCoord;
		}
		throw new RuntimeException("Out of range");
	}

	@Override
	public Vec3 support(Vec3 dir) {
		double dot = -Float.MAX_VALUE;
		int index = 0;
		for(int i = 0; i < vertices.length; i += 3){
			double newDot = dir.xCoord*vertices[i] + dir.yCoord*vertices[i+1] + dir.zCoord*vertices[i+2];
			if(newDot > dot){
				dot = newDot;
				index = i;
			}
		}
		return new Vec3(vertices[index], vertices[index+1], vertices[index+2]);
	}

	@Override
	public Collider copy() {
		ConvexMeshCollider c = new ConvexMeshCollider();
		c.vertices = vertices;
		c.indices = indices;
		c.triangles = triangles;
		c.localBox = localBox;
		c.localCentroid = localCentroid;
		c.localInertiaTensor = localInertiaTensor;
		c.mass = mass;
		return c;
	}

	@Override
	public void debugRender() {
		BufferBuilder buf = Tessellator.getInstance().getBuffer();
		GlStateManager.disableTexture2D();
		buf.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		for(Triangle t : triangles){
			buf.pos(t.p1.pos.x, t.p1.pos.y, t.p1.pos.z).color(1F, 0, 0, 1F).endVertex();
			buf.pos(t.p2.pos.x, t.p2.pos.y, t.p2.pos.z).color(1F, 0, 0, 1F).endVertex();
			buf.pos(t.p2.pos.x, t.p2.pos.y, t.p2.pos.z).color(1F, 0, 0, 1F).endVertex();
			buf.pos(t.p3.pos.x, t.p3.pos.y, t.p3.pos.z).color(1F, 0, 0, 1F).endVertex();
			buf.pos(t.p3.pos.x, t.p3.pos.y, t.p3.pos.z).color(1F, 0, 0, 1F).endVertex();
			buf.pos(t.p1.pos.x, t.p1.pos.y, t.p1.pos.z).color(1F, 0, 0, 1F).endVertex();
		}
		GlStateManager.enableTexture2D();
		Tessellator.getInstance().draw();
	}
	
}
