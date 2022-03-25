package com.wsh.settings;

import javax.annotation.PostConstruct;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wsh.model.Category;
import com.wsh.repo.RepositoryCategory;

@Component
public class InitDB {


	@Autowired
	private RepositoryCategory repositoryCategory;

@Transient
 @PostConstruct
    private void postConstruct() {
	 Category root=repositoryCategory.findByName("root");
	 if(root!=null)return;
	 root=new  Category( "root");
	 repositoryCategory.save(root);

}
}

