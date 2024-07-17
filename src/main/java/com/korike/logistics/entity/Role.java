package com.korike.logistics.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
//@Data@NoArgsConstructor@AllArgsConstructor
public class Role {

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

	    private String name;

	    @ManyToMany(mappedBy = "roles", cascade=CascadeType.ALL)
	    private Set<User> users;

	    
	    
		public Role() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Role(Long id, String name, Set<User> users) {
			super();
			this.id = id;
			this.name = name;
			this.users = users;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Set<User> getUsers() {
			return users;
		}

		public void setUsers(Set<User> users) {
			this.users = users;
		}
	    
	    
}
