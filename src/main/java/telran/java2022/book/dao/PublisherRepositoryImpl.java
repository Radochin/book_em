package telran.java2022.book.dao;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import telran.java2022.book.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<String> findPublishersByAuthor(String authorName) {
	TypedQuery<String> query = em.createQuery(
		"select distinct p.publisherName from Book b join b.authors a join b.publisher p where a.name=?1",
		String.class);
	query.setParameter(1, authorName);
	return query.getResultList();
    }

    @Override
    public Stream<Publisher> findDistinctByBooksAuthorsName(String authorName) {
	TypedQuery<Publisher> query = em.createQuery(
		"select distinct a.authorName from Book b join b.authors a join b.title a where  ", 
		Publisher.class);
	query.setParameter(1, authorName);
	return query.getResultStream();
    }

    @Override
    public Optional<Publisher> findById(String publisherName) {
	Publisher publisher = em.find(Publisher.class, publisherName);
	return Optional.ofNullable(publisher);
    }

    // TypedQuery<Publisher> query=em.createQuery("select distinct n.name from
    // Author a join a.publisher p join a.name n where p.name?1", Publisher.class);
    // query.setParameter(1, publisherName);
    // Publisher publisher = em.find(Publisher.class, publisherName);
    // return query.getSingleResult();

    }

    @Override
    public Publisher save(Publisher publisher) {
	em.persist(publisher);
	// em.merge(publisher);
	return publisher;
    }

}