package dk.itu.realms.model.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class User {

        @NotNull
        @NotEmpty
        @Email(message = "Please provide a valid email")
        private String email;

        @NotNull
        @NotEmpty
        private String password;
        
        @NotNull
        @NotEmpty
        private String name;
        
        @NotNull
        @NotEmpty
        private String address;
        
        @NotNull
        @NotEmpty
        @Length(min = 4)
        @Pattern(regexp = "[0-9]*")
        private String zipCode;
        
        @NotNull
        @NotEmpty
        @Pattern(regexp = "[0-9]*")
        private String phoneNo;

        private boolean enabled;
        
        public String getEmail() {
                return email;
        }
        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }
        public void setPassword(String password) {
                this.password = password;
        }

        public String getName() {
                return name;
        }
        public void setName(String name) {
                this.name = name;
        }

        public String getAddress() {
                return address;
        }
        public void setAddress(String address) {
                this.address = address;
        }

        public String getZipCode() {
                return zipCode;
        }
        public void setZipCode(String zipCode) {
                this.zipCode = zipCode;
        }

        public String getPhoneNo() {
                return phoneNo;
        }
        public void setPhoneNo(String phoneNo) {
                this.phoneNo = phoneNo;
        }

        public boolean getEnabled() {
                return enabled;
        }
        public void setEnabled(boolean enabled) {
                this.enabled = enabled;
        }
}