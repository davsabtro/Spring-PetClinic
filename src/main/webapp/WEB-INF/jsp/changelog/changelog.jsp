<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="changelog">

    <h1>Changelog</h1>
    <p>All changes and new features implemented will be documented in this section.</p>

    <hr>
    <div class="card">
      <h2 class="card-header">2022-05-22</h5>
      <div class="card-body">
       
        <span class="glyphicon glyphicon-plus" aria-hidden="true"> 
              
          <span class="card-text" style="font-size:16px;"> Added interface adjustment to actual plan.</span>
          
        </span>
        <br>
        <span class="glyphicon glyphicon-plus" aria-hidden="true"> 
              
          <span class="card-text" style="font-size:16px;"> Added Customer agreement in the application.</span>
          
        </span>
        <br>
        <span class="glyphicon glyphicon-plus" aria-hidden="true"> 
              
          <span class="card-text" style="font-size:16px;"> Added Support page section.</span>
          
        </span>
        
      </div>
    </div>
      <br>
    <div class="card">
      <h2 class="card-header">2022-05-21</h5>
      <div class="card-body">
       
        <span class="glyphicon glyphicon-plus" aria-hidden="true"> 
              
          <span class="card-text" style="font-size:16px;"> Added email notifications with TwilioAPI.</span>
        </span>
      </div>
    </div>
      <br>
    <div class="card">
      <h2 class="card-header">2022-05-20</h5>
      <div class="card-body">
       
        <span class="glyphicon glyphicon-plus" aria-hidden="true"> 
              <span class="card-text" style="font-size:16px;"> Added a feature to obtain a better password (passwordgen API).</span>
        </span>
      </div>
    </div>
      <br>
    <div class="card">
      <h2 class="card-header">2022-05-19</h5>
      <div class="card-body">
       
        <span class="glyphicon glyphicon-plus" aria-hidden="true"> 
              <span class="card-text" style="font-size:16px;"> Added an option to upgrade your actual plan.</span>
        </span>
      </div>
    </div>
      <br>
    <div class="card">
        <h2 class="card-header">2022-05-17</h5>
        <div class="card-body">
         
          <span class="glyphicon glyphicon-plus" aria-hidden="true"> 
                <span class="card-text" style="font-size:16px;"> Added a changelog section was implemented to keep up to date with new changes and features.</span>
          </span>
        </div>
      </div>
    

</petclinic:layout>