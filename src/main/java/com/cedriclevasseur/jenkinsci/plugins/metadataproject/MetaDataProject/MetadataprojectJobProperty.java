/*
 *     This file is part of MetaDataProject.
 * 
 *     MetaDataProject is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version
 *     Please consult http://www.gnu.org/licenses/agpl.txt or Licence.txt in this project
 *     Copyright @ 2015 Cédric Levasseur,  http://www.cedriclevasseur.com cedric.levasseur@gmail.com
 */
package com.cedriclevasseur.jenkinsci.plugins.metadataproject.MetaDataProject;

import com.cedriclevasseur.jenkinsci.plugins.metadataproject.MetaDataProject.MetadataprojectJobProperty.MetaData;
import hudson.Extension;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import hudson.model.AbstractProject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 *
 * @author Cédric Levasseur cedric.levasseur@gmail.com
 */
public class MetadataprojectJobProperty extends JobProperty<AbstractProject<?, ?>> {

    /* using a unique key/value for the moment,
     * we'll see for a map later
     */
    private final List<MetaData> listOfMetaData;

     
    @DataBoundConstructor
    public MetadataprojectJobProperty(List<MetaData> listOfMetaData) {
        this.listOfMetaData=listOfMetaData;
    }

    @Extension
    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    /* getters */

    public List<MetaData> getListOfMetaData() {
        return listOfMetaData;
    }

    
    private static final Logger LOGGER = Logger.getLogger(MetadataprojectJobProperty.class.getName());

    public static final class DescriptorImpl extends JobPropertyDescriptor {


        public DescriptorImpl() {
            super(MetadataprojectJobProperty.class);
            load();
        }

        @Override
        public JobProperty<?> newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            
            LOGGER.log(Level.WARNING,"formData= "+formData.toString());
            MetadataprojectJobProperty jobProperty = req.bindJSON(MetadataprojectJobProperty.class, formData);
            return jobProperty;
        }

        public String getDisplayName() {
            return "MetaDataProject";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject o) throws FormException {
            
            LOGGER.log(Level.WARNING,"configure with req="+req.toString());
            
            save();
            return true;
        }


    }

    /**
     * Represents key/value entries defined by users in their jobs.
     */
    public static class MetaData implements Cloneable {

        private final String key;
        private final String value;

        @DataBoundConstructor
        public MetaData(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Object clone() {
            return new MetaData(key, value);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null) {
                return false;
            }
            if(getClass() != obj.getClass()) {
                return false;
            }
            final MetaData other = (MetaData) obj;
            return StringUtils.equals(key, other.getKey()) && StringUtils.equals(value, other.getValue());

        }

        public String getKey(){
            return key;
        }
        
        public String getValue(){
            return value;
        }
        

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + (this.key != null ? this.key.hashCode() : 0);
            hash = 97 * hash + (this.value != null ? this.value.hashCode() : 0);
            return hash;
        }

        @Override
        public String toString() {
            return "MetaData{" + "key=" + key + ", value=" + value + '}';
        }
        
      
    }

    
    
}
