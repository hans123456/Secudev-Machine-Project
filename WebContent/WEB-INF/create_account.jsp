<tr>
	<td>First Name :</td>
	<td><input type="text" name="firstname" id="firstname"
		class="width-fixed" ng-model="firstname" ng-maxlength="50"
		ng-pattern="/^[a-zA-Z0-9 ]*$/" required /></td>
</tr>
<tr>
	<td></td>
	<td><small ng-cloak
		ng-show="(myform.firstname.$dirty || myform.$submitted) && myform.firstname.$error.required">Required.</small>
		<small ng-cloak ng-show="myform.firstname.$error.maxlength">Too
			long.</small> <small ng-cloak ng-show="myform.firstname.$error.pattern">No
			Special Characters.</small></td>
</tr>
<tr>
	<td>Last Name :</td>
	<td><input type="text" name="lastname" id="lastname"
		class="width-fixed" ng-model="lastname" ng-maxlength="50"
		ng-pattern="/^[a-zA-Z0-9 ]*$/" required /></td>
</tr>
<tr>
	<td></td>
	<td><small ng-cloak
		ng-show="(myform.lastname.$dirty || myform.$submitted) && myform.lastname.$error.required">Required.</small>
		<small ng-cloak ng-show="myform.lastname.$error.maxlength">Too
			long.</small> <small ng-cloak ng-show="myform.lastname.$error.pattern">No
			Special Characters.</small></td>
</tr>
<tr>
	<td>Gender :</td>
	<td><select name="gender" id="gender" required><option
				value="Male">Male</option>
			<option value="Female">Female</option></select></td>
</tr>
<tr>
	<td></td>
	<td></td>
</tr>
<tr>
	<td>Salutation :</td>
	<td><select name="salutation" id="salutation" required></select></td>
</tr>
<tr>
	<td></td>
	<td></td>
</tr>
<tr>
	<td style="vertical-align: middle">Birthdate :</td>
	<td><input type="text" name="birthdate" id="birthdate"
		ng-model="birthdate" legal-age="19" required readonly/></td>
</tr>
<tr>
	<td></td>
	<td><small ng-cloak
		ng-show="(myform.birthdate.$dirty || myform.$submitted) && myform.birthdate.$error.required">Required.</small>
		<small ng-cloak ng-show="myform.birthdate.$error.date">LOL.</small> <small
		ng-cloak ng-show="myform.birthdate.$error.legalAge">Must be above 18 years old.</small></td>
</tr>
<tr>
	<td>Username :</td>
	<td><input type="text" name="username" id="username"
		ng-model="username" ng-maxlength="50" ng-pattern="/^[a-zA-Z0-9_]*$/"
		class="width-fixed" ng-trim="false" required /></td>
</tr>
<tr>
	<td></td>
	<td><small ng-cloak
		ng-show="(myform.username.$dirty || myform.$submitted) && myform.username.$error.required">Required.</small>
		<small ng-cloak ng-show="myform.username.$error.maxlength">Too
			long.</small> <small ng-cloak ng-show="myform.username.$error.pattern">Alphanumeric
			with underscore only.</small></td>
</tr>
<tr>
	<td>Password :</td>
	<td><input type="password" name="password" id="password"
		ng-model="password" ng-maxlength="50" ng-pattern="/^\S*$/"
		class="width-fixed" required /></td>
</tr>
<tr>
	<td></td>
	<td><small ng-cloak
		ng-show="(myform.password.$dirty || myform.$submitted) && myform.password.$error.required">Required.</small>
		<small ng-cloak ng-show="myform.password.$error.maxlength">Too
			long.</small><small ng-cloak ng-show="myform.password.$error.pattern">No
			Spaces Allowed.</small></td>
</tr>
<tr>
	<td>About Me :</td>
	<td><textarea name="about_me" id="about_me" rows="10"
			style="resize: vertical" class="width-fixed" ng-model="about_me"
			ng-maxlength="1000"></textarea></td>
</tr>
<tr>
	<td></td>
	<td><small ng-cloak ng-show="myform.about_me.$error.maxlength">Too
			long.</small></td>
</tr>
<tr>
	<td></td>
	<td style="text-align: right"><input type="reset" value="Reset"
		style="margin-right: 5px" ng-click="reset()"><input
		type="submit" value="Submit"></td>
</tr>