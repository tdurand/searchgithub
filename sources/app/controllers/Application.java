package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.Commit;
import models.Commits;
import models.Contributor;
import models.Contributors;
import models.Repositories;
import models.Repository;
import play.libs.WS;
import play.libs.WS.WSRequest;
import play.mvc.Controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class Application extends Controller {
  
    /**
     * Méthode recherchant dans les repositories public de GitHub
     * @param stringSearched
     * Retourne une page html contenant les résultats
     */
    
    public static void search(String stringSearched)
    {
    	Repositories repositories=new Repositories();

    	 if(stringSearched!=null)
    	 {
    		 
	 
    	 WSRequest wsRequest = WS.url("http://github.com/api/v2/json/repos/search/%s",stringSearched); 

         JsonElement response;
         
	         try 
	         {
				response = wsRequest.get().getJson();
				
				//specification du format de la date avant deserialisation
		         Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss Z").create();
		         
		        //deserialisation du resultat
		        repositories=gson.fromJson(response,Repositories.class);
				
	         }
	         catch (Exception e) 
	         {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
	         }
    	 }
    	
         render(repositories);
    }
    
    /**
     * Méthode affichant les détails d'un repository de GitHub
     * @param owner
     * @param name
     */
    
    public static void showRepository(String owner,String name)
    {
    	
    	Repositories repositories=new Repositories();
    	Repository repository=new Repository();

   	 if(owner!=null && name!=null)
   	 {
   		 
	 
   		 WSRequest wsRequest = WS.url("http://github.com/api/v2/json/repos/show/%s/%s", owner, name); 

         JsonElement response;
        
	         try 
	         {
				response = wsRequest.get().getJson();
				
				//specification du format de la date avant deserialisation
		         Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss Z").create();
		         
		        //deserialisation du resultat
		         repositories=gson.fromJson(response,Repositories.class);
		         repository=repositories.getRepository();
	         }
	         catch (Exception e) 
	         {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
	         }
   	 	}
   	
        render(repository);	
    }
    
    /**
     * Méthode affichant les X derniers Commits d'un repo (ou X=size)
     * @param owner
     * @param name
     * @param size
     */
    public static void latestCommits(String owner,String name,int size)
    {
		
		List<Commit> commits=new ArrayList<Commit>();
		
		 int i=1;
		 WSRequest wsRequest;
		 JsonElement response;
		 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
	 
		 while(commits.size()<size)
		{
			 try {
					
				 	wsRequest = WS.url("http://github.com/api/v2/json/commits/list/%s/%s/master?page="+i, owner, name);
					response = wsRequest.get().getJson();
					
					commits.addAll(gson.fromJson(response,Commits.class).getCommits());
				
					i++;
				} 
				catch (Exception e) {
					//no commits found
					render(commits);
				}
		}
		
		if(commits.size()>size)
		{
			commits=commits.subList(0, size);
		}
		

		render(commits);
    	
    }
    
    
    /**
     * Méthode affichant tous les contributeur d'un repository
     * @param owner
     * @param name
     */
    public static void listContributors(String owner,String name)
    {
    	Contributors contributors=new Contributors();

	   	 if(owner!=null && name!=null)
	   	 {
	   		 
		 
	   		 WSRequest wsRequest = WS.url("http://github.com/api/v2/json/repos/show/%s/%s/contributors", owner, name); 	
	
	         JsonElement response;
	        
		         try 
		         {
					response = wsRequest.get().getJson();
					
			         Gson gson = new GsonBuilder().create();
			         
			        //deserialisation du resultat
			         contributors=gson.fromJson(response,Contributors.class);
			        
		         }
		         catch (Exception e) 
		         {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
		         }
	   	 	}
	   	 
	   	 List<Contributor> listContributors=contributors.getContributors();
   	
        render(listContributors);	
   
    
    }
    
    /**
     * Méthode affichant l'impact des Committers en % sur le projet en se basant sur les 
     * 100 derniers commits.
     * @param owner
     * @param name
     * @param size
     */
    public static void impactCommitters(String owner,String name,int size)
    {
    	
		
    	List<Commit> commits=new ArrayList<Commit>();
		
		 int i=1;
		 WSRequest wsRequest;
		 JsonElement response;
		 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		 
		 Map<String,Float> usersCommits = new HashMap<String,Float>();
			
		 Float nbCommit;
	 
		 while(commits.size()<size)
		{
			 try {
					
				 	wsRequest = WS.url("http://github.com/api/v2/json/commits/list/%s/%s/master?page="+i, owner, name);
					response = wsRequest.get().getJson();
					
					commits.addAll(gson.fromJson(response,Commits.class).getCommits());
				
					i++;
				} 
				catch (Exception e) {
					//no commits found
					render(usersCommits);
				}
		}
		
		if(commits.size()>size)
		{
			commits=commits.subList(0, size);
		}

		
		for (Iterator iterator = commits.iterator(); iterator.hasNext();) {
			Commit commit = (Commit) iterator.next();
			
				   //Recupere le précédent nombre de commit de la personne
				   nbCommit=usersCommits.get(commit.getCommitter().getName());
			
				   if(nbCommit!=null)
				   {
					   usersCommits.put(commit.getCommitter().getName(), nbCommit+1);
				   }
				   else
				   {
					   usersCommits.put(commit.getCommitter().getName(), (float) 1);
				   }   
			
		}
		
		//Conversion en pourcentage
		for (Map.Entry<String,Float> entry : usersCommits.entrySet())
		{
		    	entry.setValue(entry.getValue()*100/commits.size());
		}
		
		render(usersCommits);

    	
    }

}