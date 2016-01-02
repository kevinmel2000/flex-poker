<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<title>Signup Page</title>
</head>
<body>

<h3>Signup</h3>

<form action="<c:url value='sign-up' />" method='POST'>

<c:if test="${not empty error}">
   <p style="color:red">Error: ${error}</p>
</c:if>

  <div>
    <label>User:</label> <input type="text" name="username" />
  </div>
  <div>
    <label>Password:</label> <input type="password" name="password" />
  </div>
  <div>
    <label>Email:</label> <input type="text" name="emailAddress" />
  </div>

  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

  <div>
    <input name="submit" type="submit" value="Login" />
  </div>

</form>

</body>
</html>
