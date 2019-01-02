package com.example.ijp.scalaspringexample

import java.lang.annotation.Documented

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.bson.types.ObjectId
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.context.event.EventListener
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.{GetMapping, RestController}
import reactor.core.publisher.Flux

@SpringBootApplication
class ScalaSpringExampleApplication(private final val personRepo: PersonRepo) {
  @EventListener(Array(classOf[ApplicationStartedEvent]))
  def addDataIntoDb(): Unit = {

    val fluxOfPersons: Flux[Person] = Flux.just(Person(new ObjectId, "Doe", "John"), Person(new ObjectId, "Pitt", "Brad"))
      .flatMap(p => personRepo.save(p))
    fluxOfPersons.subscribe(p => println(s"saving $p"))
  }
}

object ScalaSpringExampleApplication extends App {
  SpringApplication.run(classOf[ScalaSpringExampleApplication])
}

@RestController
class PersonRestController(private final val personRepo: PersonRepo) {

  @GetMapping(Array("/persons"))
  def find(): Flux[Person] = {
    personRepo.findAll()
  }
}

//TODO: find a way for mongo to manage option for the Id
@Documented
case class Person(@Id id: ObjectId, name: String, firstName: String)


@Repository
trait PersonRepo extends ReactiveMongoRepository[Person, ObjectId]

@Configuration
class JacksonConfig {
  @Bean def scalaModule = new DefaultScalaModule()
}