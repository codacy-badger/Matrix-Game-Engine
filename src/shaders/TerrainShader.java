package shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class TerrainShader extends ShaderProgram {
	
	private static final int MAX_LIGHT = 4;
	
	private static final String VERTEX_FILE ="src/shaders/terrainVertexShader.txt";
	private static final String FRAGMENT_FILE ="src/shaders/terrainFragmentShader.txt";
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColor[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	private int location_backgroundTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendMap;
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
	
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normals");
	}

	@Override
	protected void getAllUniformLocation() {
		
		// TODO Auto-generated method stub
		location_transformationMatrix = super.getUnitformLocation("transformationMatrix");
		location_projectionMatrix = super.getUnitformLocation("projectionMatrix");
		location_viewMatrix = super.getUnitformLocation("viewMatrix");
		
		location_shineDamper = super.getUnitformLocation("shineDamper");
		location_reflectivity = super.getUnitformLocation("reflectivity");
		location_skyColor = super.getUnitformLocation("skyColor");
		
		location_backgroundTexture = super.getUnitformLocation("backgroundTexture");
		location_rTexture = super.getUnitformLocation("rTexture");
		location_gTexture = super.getUnitformLocation("gTexture");
		location_bTexture = super.getUnitformLocation("bTexture");
		location_blendMap = super.getUnitformLocation("blendMap");
		
		location_lightPosition = new int[MAX_LIGHT];
		location_lightColor = new int[MAX_LIGHT];
		location_attenuation = new int[MAX_LIGHT];
		
		
		for(int i=0;i<MAX_LIGHT;i++) {
			
			location_lightPosition[i] =super.getUnitformLocation("lightPosition["+i+"]");
			location_lightColor[i] = super.getUnitformLocation("lightColor["+i+"]");
			location_attenuation[i] = super.getUnitformLocation("attenuation["+i+"]");
			
			
		}
		
		
	}
	
	
	public void loadTransfromationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadLight(List<Light> lights) {
		
		for(int i=0;i<MAX_LIGHT;i++) {
			//may be if lights are less then four
		if(i<lights.size()) {	
		super.loadVector(location_lightPosition[i],lights.get(i).getPosition());
		super.loadVector(location_lightColor[i],lights.get(i).getColor());
		super.loadVector(location_attenuation[i],lights.get(i).getAttenuation());
		
		}else {
			super.loadVector(location_lightPosition[i], new Vector3f(0,0,0));
			super.loadVector(location_lightColor[i], new Vector3f(0,0,0));
			super.loadVector(location_attenuation[i],new Vector3f(1,0,0));
			
		}
		
		}
		
	}
	
	public void loadShineVariable(float damper,float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
		
	}
	public void loadSkyColor(float r,float g,float b) 
	{
		super.loadVector(location_skyColor,new Vector3f(r,g,b));
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_backgroundTexture, 0);
		super.loadInt(location_rTexture, 1);
		super.loadInt(location_gTexture, 2);
		super.loadInt(location_bTexture, 3);
		super.loadInt(location_blendMap, 4);
			
		
		
	}
	
	

}