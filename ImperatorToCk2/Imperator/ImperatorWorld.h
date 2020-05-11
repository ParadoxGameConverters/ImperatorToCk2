#ifndef IMPERATOR_WORLD
#define IMPERATOR_WORLD



#include <string>



namespace ImperatorWorld
{

class World
{
  public:
	[[nodiscard]] const auto& getSaveName() const { return saveName; }

  private:
	std::string saveName = "CK2tester";
};

} // namespace ImperatorWorld



#endif // IMPERATOR_WORLD
