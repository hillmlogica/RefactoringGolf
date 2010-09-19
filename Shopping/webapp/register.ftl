<html>
  <head>
    <title>Please register below</title>
  </head>
  <body>
	<p>
	    <form name="register" method="POST" action="register">
	      <label for="firstname">Firstname:</label><input name="firstname" value="${firstname!}"><br />
	      <label for="username">Username:</label><input name="username" value="${username!}"><br />
	      <label for="password">Password:</label><input type="password" name="password" value="${password!}"><br />
	      <input type="submit" name="registerButton" value="Register"/>
	    </form>
	    <#list actionErrors as error>
		  <span name="error" class="error" style="color:red">* ${error}</span><br/>
	    </#list>
 	</p>
  </body>
</html>