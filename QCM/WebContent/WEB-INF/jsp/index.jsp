<jsp:include page="include/header.jsp" />
	<%@ include file="include/taglib.jsp" %>
	<ul id="advice">
		<li>
			<h3>Statistiques</h3>
			<strong>${nbQuestionnaires}</strong> questionnaire(s)<br/>
			<strong>${nbUsers}</strong> utilisateur(s)<br/>
			<strong>${nbTakenQCM}</strong> QCM pass�s
		</li>
		
		<li>
			<h3>Qu'est-ce qu'un QCM ?</h3>
			<p>Un questionnaire � choix multiples (ou QCM) est un questionnaire dans lequel sont propos�es plusieurs r�ponses pour chaque question.</p>
			<p>Une ou plusieurs de ces r�ponses sont correctes.</p>
		</li>
	</ul>
	
	<div id="content">
		<h2><c:out value="Derniers QCM ajout�s" /></h2>
			<c:forEach items="${listLastQCM}" var="questionnaire">
				<div class="list-box">
					<div class="list-stat answered">
						<span class="list-count">
							${questionnaire.resultsSize}
						</span>
						hits
					</div>	
					
					<h4>
						<a href="<spring:url value="/questionnaire/${questionnaire.id}" />"> 
							${questionnaire.title}
						</a>
					</h4>
					${questionnaire.description}
					
					<div class="tags">
						<c:forEach items="${questionnaire.tags}" var="tag">
							<span class="tag">${tag}</span>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
		
		<h2><c:out value="QCM les plus jou�s !" /></h2>
			<c:forEach items="${listPopularQCM}" var="questionnaire">
				<div class="list-box">
					<div class="list-stat answered">
						<span class="list-count">
							${questionnaire.resultsSize}
						</span>
						hits
					</div>	
					
					<h4>
						<a href="<spring:url value="/questionnaire/${questionnaire.id}" />"> 
							${questionnaire.title}
						</a>
					</h4>
					${questionnaire.description}
					
					<div class="tags">
						<c:forEach items="${questionnaire.tags}" var="tag">
							<span class="tag">${tag}</span>
						</c:forEach>
					</div>	
				</div>
			</c:forEach>
	</div>
	
<jsp:include page="include/footer.jsp" />
