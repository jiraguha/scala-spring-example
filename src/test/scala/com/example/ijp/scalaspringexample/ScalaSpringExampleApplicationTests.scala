package com.example.ijp.scalaspringexample

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(Array(classOf[SpringExtension]))
class ScalaSpringExampleApplicationTests {
  @Test
  def contextLoads(): Unit = {}
}
