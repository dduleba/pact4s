import sbt._

object Dependencies {

  val mockito = "4.8.0"

  val pactJvm = "4.3.15"

  val http4s = "1.0.0-M30"

  val log4s = "1.10.0"

  val logback = "1.4.4"

  val collectionCompat = "2.8.1"

  val sourcecode = "0.3.0"

  val _circe = "0.14.3"

  val _playJson = "2.9.3"

  val _weaver = "0.8.0"

  val _scalatest = "3.2.14"

  val _munit = "0.7.29"

  val munitCatsEffect = "1.0.7"

  val shared: Seq[ModuleID] =
    Seq(
      "au.com.dius.pact"        % "consumer"                % pactJvm,
      "au.com.dius.pact"        % "provider"                % pactJvm,
      "org.log4s"              %% "log4s"                   % log4s,
      "ch.qos.logback"          % "logback-classic"         % logback         % Runtime,
      "org.scala-lang.modules" %% "scala-collection-compat" % collectionCompat,
      "com.lihaoyi"            %% "sourcecode"              % sourcecode,
      "org.http4s"             %% "http4s-ember-client"     % http4s          % Test,
      "org.http4s"             %% "http4s-dsl"              % http4s          % Test,
      "org.http4s"             %% "http4s-ember-server"     % http4s          % Test,
      "org.http4s"             %% "http4s-circe"            % http4s          % Test,
      "io.circe"               %% "circe-core"              % _circe          % Test,
      "org.mockito"             % "mockito-core"            % mockito         % Test,
      "org.typelevel"          %% "munit-cats-effect-3"     % munitCatsEffect % Test
    )

  val munit: Seq[ModuleID] = Seq(
    "org.typelevel" %% "munit-cats-effect-3" % munitCatsEffect % Provided
  )

  val scalatest: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % _scalatest % Provided
  )

  val weaver: Seq[ModuleID] = Seq(
    "com.disneystreaming" %% "weaver-core" % _weaver % Provided,
    "com.disneystreaming" %% "weaver-cats" % _weaver % Test
  )

  val circe: Seq[ModuleID] = Seq(
    "io.circe"      %% "circe-core"   % _circe,
    "io.circe"      %% "circe-parser" % _circe,
    "org.scalameta" %% "munit"        % _munit % Test
  )

  val playJson: Seq[ModuleID] = Seq(
    ("com.typesafe.play" %% "play-json" % _playJson).cross(CrossVersion.for3Use2_13),
    "org.scalameta"      %% "munit"     % _munit % Test
  )

  val example: Seq[ModuleID] = Seq(
    "org.http4s"    %% "http4s-ember-client" % http4s,
    "org.http4s"    %% "http4s-dsl"          % http4s,
    "org.http4s"    %% "http4s-ember-server" % http4s,
    "org.http4s"    %% "http4s-circe"        % http4s,
    "io.circe"      %% "circe-core"          % _circe,
    "org.typelevel" %% "munit-cats-effect-3" % munitCatsEffect % Test,
    "org.scalatest" %% "scalatest"           % _scalatest      % Test
  )

}
