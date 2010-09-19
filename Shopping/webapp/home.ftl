<html>
  <head>
    <title>Welcome to Shopping!</title>
  </head>
  <body>
    <#if loggedIn>
    	<span id="welcomeMessage">Hi ${user.firstname}!</span><span style="float:right;"><a href="logout">logout</a></span>
    	<br/>
    	<p>Please buy stuff below. Your current basket total: <span id="basketPrice">${basket.price}</span> (${basket.entries.size()} items)</span></p>
    	<p><form name="placeOrder" method="POST" action="order">When you are ready to place the order, confirm here <input name="submitOrder" type="submit" value="Place Order"/></form></p>
    	<table>
    	  <tr><th>Product</th><th>Price</th><th>Quantity</th><th/></tr>
    	<#list products as product>
    	  <form name="product_${product.name}" method="POST" action="basket!doAdd">
       	  <tr>
       	    <input type="hidden" name="productId" value="${product.id}" />
       	    <td>${product.name}</td>
       	    <td style="padding-left:20px" align="right">${product.price}</td>
    	    <td align="right"><input name="quantity" size="5"></td>
    	    <td>&nbsp;<input type="submit" name="add" value="Add to Basket"/></td>
          </tr>  
    	  </form>
    	</#list>
    	</table>
    	<hr/>
    	<h3>Your previous orders...</h3>
    	<table border="2" cellpadding="10">
    	<#list orders as order>
    	  <tr><td>
    	  <h3 name="orderSummary">Order ${order.id} (${order.displayableTimeOfOrder})</h3>
    	  <table>
    	  <tr><th style="border-bottom:1px solid;">Product</th><th style="border-bottom:1px solid;">Quantity</th><th style="border-bottom:1px solid;">Price</th><th style="border-bottom:1px solid;">Total</th></tr>
    	  <#list order.lines as line>
      	    <tr><td>${line.product.name}</td><td style="padding-left:10px" align="right">${line.quantity}</td><td style="padding-left:10px" align="right">${line.product.price}</td><td style="padding-left:10px" align="right">${line.price}</td></tr>    	  
    	  </#list>
    	  <tr style="font-weight:bold"><td style="border-top:1px solid;">Total</td><td align="right"  style="border-top:1px solid;">${order.quantity}</td><td  style="border-top:1px solid;">&nbsp;</td><td  style="border-top:1px solid;" align="right">${order.price}</td></tr>
      	  </table>
      	  </td></tr>
    	</#list>
    	</table>
    <#else>
    	<p><span id="welcomeMessage">Hi there! Please login below or <a id="registrationLink" href="register!doNew">register</a></span></p>
    	<p>
	        <form name="login" method="POST" action="login">
	          <label for="username">Username:</label><input name="username" value="${username!}"><br />
	          <label for="password">Password:</label><input type="password" name="password" value="${password!}"><br />
	          <input type="submit" name="loginButton" value="Login"/>
	        </form>
	        <#list actionErrors as error>
        	  <span name="error" class="error" style="color:red">* ${error}</span><br/>
    	    </#list>
    	</p>
    </#if>
  </body>
</html>