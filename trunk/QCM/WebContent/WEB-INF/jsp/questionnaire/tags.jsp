<%@include file="../include/taglib.jsp" %>
<c:forEach items="${questionnaire.tags}" var="tag">
	<span class="deleteTag">
		<c:out value="${tag}" />
		<a href="#" rel="${tag}">X</a>
	</span>
</c:forEach>