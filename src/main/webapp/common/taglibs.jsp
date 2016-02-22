<%@page import="java.util.Locale"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springmodules.org/tags/commons-validator" prefix="v" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib uri="http://www.appfuse.org/tags/spring" prefix="appfuse" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="datePattern"><fmt:message key="date.format"/></c:set>
<c:set var="now" value="<%=new java.util.Date()%>" />

<c:set var="keywordsGenerica" value="dogsitter, dog sitter, lavoro dog sitter, cerco lavoro dog sitter, pensione, pensione cani, pensioni per cani, pensione cane, cane pensione, adozione cani, adozione cane, associazione, associazioni, associazioni di volontariato cani, associazione di volontariato cani, cane, cani, pet, pets, animali cani, cani, cane, tutto sui cani, tutto cani, tutto cane, tutto sul cane, foto cani, cuccioli, cani italiani, cani da lavoro, cani da compagnia, cuccioli di cane, annunci cani, canili, canile, cinofilia"/>

<c:set var="keywordsDogSitter" value="dogsitter, dog sitter, lavoro dog sitter, cane, cani, pet, pets, animali cani, cani, cane, tutto sui cani, tutto cani, tutto cane, tutto sul cane, foto cani, cuccioli, cani italiani, cani da lavoro, cani da compagnia, cuccioli di cane, annunci cani, canili, canile, cinofilia"/>
<c:set var="keywordsDogHost" value="pensione, pensione cani, pensioni per cani, pensione cane, cane pensione, cane, cani, pet, pets, animali cani, cani, cane, tutto sui cani, tutto cani, tutto cane, tutto sul cane, foto cani, cuccioli, cani italiani, cani da lavoro, cani da compagnia, cuccioli di cane, annunci cani, canili, canile, cinofilia"/>
<c:set var="keywordsAdozione" value="adozione cani, adozione cane, pelo, pelo corto, pelo lungo, cane piccola taglia, cane media taglia, cane grande taglia, carattere, cane, cani, pet, pets, animali cani, cani, cane, tutto sui cani, tutto cani, tutto cane, tutto sul cane, foto cani, cuccioli, cani italiani, cani da lavoro, cani da compagnia, cuccioli di cane, annunci cani, canili, canile, cinofilia"/>
<c:set var="keywordsAssociazione" value="associazione, associazioni, associazioni di volontariato cani, associazione di volontariato cani, cani, pet, pets, animali cani, cani, cane, tutto sui cani, tutto cani, tutto cane, tutto sul cane, foto cani, cuccioli, cani italiani, cani da lavoro, cani da compagnia, cuccioli di cane, annunci cani, canili, canile, cinofilia"/>
