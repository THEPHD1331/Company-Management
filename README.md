# Company-Management

A Spring Boot MVC Project with IAM Security and custom login page. 

## Featues:
- It consists of 3 users - Employee, Manager and Admin.
- Each have thier unique credentials.
- They can access URLs based on thier roles.  For unauthorized user, they see access denied page.
- Content on the home page is displayed according to thier roles. For unauthorized user, it is hidden.
- Encrypted(BCrypt) passwords are stored in database safely.

### To Be Added:
- Registeration form for users to save them in database.
- Additional roles can be added.
