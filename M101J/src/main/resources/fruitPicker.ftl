<html>
	<head>
		<title>Fruit Picker Application</title>
	</head>
	<body>
		<form method="post" action="/favorite_fruit">
			<p>What is your favorite fruit? </p>
			<#list fruits as fruit>
			<p>
				<input type="radio" name="fruit" value="${fruit}"> ${fruit} </input>
			</p>
			</#list>
			<input type="submit" value="submit"/>
		</form>
	</body>
</html>