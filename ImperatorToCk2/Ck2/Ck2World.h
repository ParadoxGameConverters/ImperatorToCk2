#ifndef CK2_WORLD
#define CK2_WORLD



#include "../Imperator/ImperatorWorld.h"



namespace Ck2World
{

class World
{
  public:
	explicit World(const ImperatorWorld::World& impWorld): modName(impWorld.getSaveName()) {}

	[[nodiscard]] std::string getModName() const { return modName; }

  private:
	std::string modName;
};

} // namespace Ck2World



#endif // CK2_WORLD