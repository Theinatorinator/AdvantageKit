// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

#include "CommandTestBase.h"
#include "frc2/command/WaitUntilCommand.h"

using namespace frc2;
class WaitUntilCommandTest : public CommandTestBase {};

TEST_F(WaitUntilCommandTest, WaitUntilCommandScheduleTest) {
  CommandScheduler scheduler = GetScheduler();

  bool finished = false;

  WaitUntilCommand command([&finished] { return finished; });

  scheduler.Schedule(&command);
  scheduler.Run();
  EXPECT_TRUE(scheduler.IsScheduled(&command));
  finished = true;
  scheduler.Run();
  EXPECT_FALSE(scheduler.IsScheduled(&command));
}
