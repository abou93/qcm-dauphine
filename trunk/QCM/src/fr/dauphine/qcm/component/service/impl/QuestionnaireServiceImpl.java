package fr.dauphine.qcm.component.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.dauphine.qcm.component.repository.IQuestionnaireRepository;
import fr.dauphine.qcm.component.repository.IResultRepository;
import fr.dauphine.qcm.component.service.IQuestionnaireService;
import fr.dauphine.qcm.model.Questionnaire;
import fr.dauphine.qcm.model.Result;
import fr.dauphine.qcm.model.User;

@Service
public class QuestionnaireServiceImpl implements IQuestionnaireService {

	@Autowired
	IResultRepository resultRepository;

	@Autowired
	IQuestionnaireRepository questionnaireRepository;

	@Override
	@Transactional(readOnly = true)
	public Result getResultByUserAndQuestionnaireId(User user,
			Long questionnaireId) {
		Result result = resultRepository.loadByUserIdAndQuestionnaireId(user
				.getId(), questionnaireId);

		if (result != null) {
			result.getQuestionnaire().shuffleQuestions();
		}

		return result;
	}

	@Override
	@Transactional
	public void saveAnswers(Result result) {
		resultRepository.save(result);
	}

	@Override
	@Transactional
	public void saveQuestionnaire(Questionnaire questionnaire) {
		questionnaireRepository.saveOrUpdate(questionnaire);
	}

	@Override
	@Transactional(readOnly = true)
	public Questionnaire getQuestionnaireById(Long id) {
		Questionnaire questionnaire = questionnaireRepository.load(id);

		if (questionnaire != null) {
			questionnaire.getTags().size(); // Lazy
			
			questionnaire.shuffleQuestions();
			
			questionnaire = questionnaireRepository.unproxy(questionnaire);
		}

		return questionnaire;
	}

	@Override
	@Transactional(readOnly = true)
	public Questionnaire getQuestionnaireByIdAndUser(Long id, User user) {
		Questionnaire questionnaire = questionnaireRepository.loadForUser(id,
				user.getId());

		if (questionnaire != null) {
			questionnaire.getTags().size(); // Lazy
			
			questionnaire.shuffleQuestions();
			
			questionnaire = questionnaireRepository.unproxy(questionnaire);
		}

		return questionnaire;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Questionnaire> getListQuestionnaire(Integer page, boolean admin) {
		return loadTags(questionnaireRepository.paginateListQuestionnaire(page, admin));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long getNbQuestionnaires() {
		return questionnaireRepository.getNbQuestionnaires();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long getNbResults() {
		return resultRepository.getNbResults();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Questionnaire> getLastQuestionnaires(boolean admin) {
		return loadTags(questionnaireRepository.getLastQuestionnaires(admin));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Questionnaire> getPopularQuestionnaires(boolean admin) {
		return loadTags(questionnaireRepository.getPopularQuestionnaires(admin));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long getNbQuestionnairesValid(boolean admin) {
		return questionnaireRepository.getNbQuestionnairesValid(admin);
	}
	
	private List<Questionnaire> loadTags(List<Questionnaire> questionnaires) {
		for (Questionnaire questionnaire : questionnaires) {
			questionnaire.getTags().size(); // Lazy
		}
		
		return questionnaires;
	}
}
