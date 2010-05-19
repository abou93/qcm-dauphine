<jsp:include page="include/header.jsp" />
	<%@ include file="include/taglib.jsp" %>
	<c:if test="${not empty result}">
		<ul id="advice">
			<li>
				<h3>score</h3>
				
				<c:out value="${fn:length(result.correctAnswers)}" />
					sur 
				<c:out value="${fn:length(result.questionnaire.questions)}" />
			</li>
			<li>
				<h3>tags</h3>
				
				<c:forEach items="${result.questionnaire.tags}" var="tag">
					<span class="deleteTag">
						${tag}
					</span>
				</c:forEach>
			</li>
		</ul>
	</c:if>
	<div id="content">
		<c:choose>
			<c:when test="${empty result}">
				Vous n'avez pas encore r�pondu � ce questionnaire.
			</c:when>
			<c:otherwise>
				<h2><c:out value="${result.questionnaire}" /></h2>
				<ul>
					<c:forEach items="${result.questionnaire.questions}" var="question" varStatus="statusQ">
						<li class="questions">
							<h3>${statusQ.index+1}. ${question}</h3>
							
							<div class="answers">
								<ul>
									<c:forEach items="${question.answers}" var="answer" varStatus="statusA">
										<li>
											${statusA.index+1}. ${answer}
											<c:if test="${answer.correct}"> (Correct)</c:if>
											<c:if test="${not answer.correct and dauphine:contains(result.answers, answer)}"> (Incorrect)</c:if>
										</li>
									</c:forEach>
								</ul>
							</div>
						</li>
					</c:forEach>
				</ul>
			</c:otherwise>
		</c:choose>
	</div>
<jsp:include page="include/footer.jsp" />
