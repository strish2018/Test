package com.strish.android.test.api;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.strish.android.test.MediaItem;

public class Response{

	@SerializedName("per_facet")
	private List<String> perFacet;

	@SerializedName("org_facet")
	private List<String> orgFacet;

	@SerializedName("column")
	private Object column;

	@SerializedName("abstract")
	private String mAbstract;

	@SerializedName("section")
	private String section;

	@SerializedName("source")
	private String source;

	@SerializedName("asset_id")
	private long assetId;

	@SerializedName("media")
	private List<MediaItem> media;

	@SerializedName("type")
	private String type;

	@SerializedName("title")
	private String title;

	@SerializedName("des_facet")
	private List<String> desFacet;

	@SerializedName("url")
	private String url;

	@SerializedName("adx_keywords")
	private String adxKeywords;

	@SerializedName("geo_facet")
	private String geoFacet;

	@SerializedName("id")
	private long id;

	@SerializedName("byline")
	private String byline;

	@SerializedName("published_date")
	private String publishedDate;

	@SerializedName("views")
	private int views;

	public void setPerFacet(List<String> perFacet){
		this.perFacet = perFacet;
	}

	public List<String> getPerFacet(){
		return perFacet;
	}

	public void setOrgFacet(List<String> orgFacet){
		this.orgFacet = orgFacet;
	}

	public List<String> getOrgFacet(){
		return orgFacet;
	}

	public void setColumn(Object column){
		this.column = column;
	}

	public Object getColumn(){
		return column;
	}

	public void setMAbstract(String mAbstract){
		this.mAbstract = mAbstract;
	}

	public String getMAbstract(){
		return mAbstract;
	}

	public void setSection(String section){
		this.section = section;
	}

	public String getSection(){
		return section;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setAssetId(long assetId){
		this.assetId = assetId;
	}

	public long getAssetId(){
		return assetId;
	}

	public void setMedia(List<MediaItem> media){
		this.media = media;
	}

	public List<MediaItem> getMedia(){
		return media;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setDesFacet(List<String> desFacet){
		this.desFacet = desFacet;
	}

	public List<String> getDesFacet(){
		return desFacet;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setAdxKeywords(String adxKeywords){
		this.adxKeywords = adxKeywords;
	}

	public String getAdxKeywords(){
		return adxKeywords;
	}

	public void setGeoFacet(String geoFacet){
		this.geoFacet = geoFacet;
	}

	public String getGeoFacet(){
		return geoFacet;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getId(){
		return id;
	}

	public void setByline(String byline){
		this.byline = byline;
	}

	public String getByline(){
		return byline;
	}

	public void setPublishedDate(String publishedDate){
		this.publishedDate = publishedDate;
	}

	public String getPublishedDate(){
		return publishedDate;
	}

	public void setViews(int views){
		this.views = views;
	}

	public int getViews(){
		return views;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"per_facet = '" + perFacet + '\'' + 
			",org_facet = '" + orgFacet + '\'' + 
			",column = '" + column + '\'' + 
			",mAbstract = '" + mAbstract + '\'' + 
			",section = '" + section + '\'' + 
			",source = '" + source + '\'' + 
			",asset_id = '" + assetId + '\'' + 
			",media = '" + media + '\'' + 
			",type = '" + type + '\'' + 
			",title = '" + title + '\'' + 
			",des_facet = '" + desFacet + '\'' + 
			",url = '" + url + '\'' + 
			",adx_keywords = '" + adxKeywords + '\'' + 
			",geo_facet = '" + geoFacet + '\'' + 
			",id = '" + id + '\'' + 
			",byline = '" + byline + '\'' + 
			",published_date = '" + publishedDate + '\'' + 
			",views = '" + views + '\'' + 
			"}";
		}
}