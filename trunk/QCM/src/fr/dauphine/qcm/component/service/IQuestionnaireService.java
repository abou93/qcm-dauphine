package fr.dauphine.qcm.component.service;

import java.util.List;

import fr.dauphine.qcm.model.Questionnaire;
import fr.dauphine.qcm.model.Result;
import fr.dauphine.qcm.model.User;

public interface IQuestionnaireService {

	Result getResultByUserAndQuestionnaireId(User user, Long questionnaireId);

	Questionnaire getQuestionnaireByIdAndUser(Long id, User user);

	Questionnaire getQuestionnaireById(Long id);

	void saveAnswers(Result result);

	void saveQuestionnaire(Questionnaire questionnaire);

	List<Questionnaire> getListQuestionnaire(Integer page);
	
	Long getNbQuestionnairesValid();
	
	Long getNbQuestionnaires();

	Long getNbResults();
	
	List<Questionnaire> getLastQuestionnaires();
	
	List<Questionnaire> getPopularQuestionnaires();
}
