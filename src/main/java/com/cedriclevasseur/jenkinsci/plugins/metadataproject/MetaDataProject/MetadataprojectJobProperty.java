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

import hudson.Extension;
import static hudson.Util.fixEmptyAndTrim;
import static hudson.Util.fixEmpty;
import hudson.Extension;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import hudson.model.AbstractProject;
import hudson.model.Job;
import java.util.Map;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 *
 * @author Cédric Levasseur cedric.levasseur@gmail.com
 */
public class MetadataprojectJobProperty extends JobProperty<AbstractProject<?, ?>> {

    private boolean metadataprojectEnabled = false;
    private Map<String, String> metadataprojectEnabledMap = null;

    @DataBoundConstructor
    public MetadataprojectJobProperty(boolean metadataprojectEnabled) {
        this.metadataprojectEnabled = metadataprojectEnabled;
    }

    @Extension
    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    public boolean isMetadataprojectEnabled() {
        return metadataprojectEnabled;
    }

    public void setMetadataprojectEnabled(boolean metadataprojectEnabled) {
        this.metadataprojectEnabled = metadataprojectEnabled;
    }

    public Map<String, String> getMetadataprojectEnabledMap() {
        return metadataprojectEnabledMap;
    }

    public void setMetadataprojectEnabledMap(Map<String, String> metadataprojectEnabledMap) {
        this.metadataprojectEnabledMap = metadataprojectEnabledMap;
    }

    public static final class DescriptorImpl extends JobPropertyDescriptor {

        /* using a unique key/value for the moment,
         * we'll see for a map later
         */
        private String key1;
        private String value1;

        public DescriptorImpl() {
            super(MetadataprojectJobProperty.class);
            load();
        }

        @Override
        public JobProperty<?> newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            if (formData.has("metadataprojectEnabled")) {
                formData.put("metadataprojectEnabled", true);
            } else {
                formData.put("metadataprojectEnabled", false);
            }
            MetadataprojectJobProperty jobProperty = req.bindJSON(MetadataprojectJobProperty.class, formData);
            return jobProperty;
        }

        public String getDisplayName() {
            return "MetaDataProject";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject o) throws FormException {

            key1 = fixEmpty(req.getParameter("metadataproject.metadata_key1"));
            value1 = fixEmpty(req.getParameter("metadataproject.metadata_value1"));

            save();
            return true;
        }

        public String getKey1() {
            return key1;
        }

        public void setKey1(String key1) {
            this.key1 = key1;
        }

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }
    }

}
